package com.br.rafael.pong.activity;

import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.rafael.pong.multiplayer.threads.ClienteBluetooth;
import com.br.rafael.pong.multiplayer.threads.MultiplayerGame;
import com.br.rafael.util.TransitaAtributos;

public class ClienteActivity extends Activity {

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";	
    public static String CONNECT_ERROR = "erro_conexao";	
	
	//Recebe a representacao do BT do celular
	public BluetoothAdapter adaptadorBT;	
	
	//Mantem instancia do modal
	private ProgressDialog progressDialog;	
	
	//Define aparelho descoberto pelo BT
	private BluetoothDevice aparelhoDescoberto;	
	
	//Mantem uma copia da atividade para enviar por listeners
	public ClienteActivity atividade = this;
	
	//Declara ids para as atividades chamadas
	private static final int ACT_MULTIPLAYER_GAME = 1;	
	
	//Arrays que armazenam a lista de aparelhos
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
		
    // The on-click listener for all devices in the ListViews
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        	
            // Cancel discovery because it's costly and we're about to connect
        	adaptadorBT.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Get the BLuetoothDevice object
            aparelhoDescoberto = adaptadorBT.getRemoteDevice(address);
            
        	//Inicia e starta a thread de conexao
            modalConectaAparelhos(info);
        	ClienteBluetooth clienteBluetooth = new ClienteBluetooth(atividade, aparelhoDescoberto);
        	clienteBluetooth.start();            
        }
    };	
    
    // The BroadcastReceiver that listens for discovered devices and changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            	
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                	mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                	finalizaModal(false);
                	Toast.makeText(context, R.string.client_new_devices_found, Toast.LENGTH_LONG).show();                 	
                }
                
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            	finalizaModal(false);
            	Toast.makeText(context, R.string.end_search, Toast.LENGTH_LONG).show();      
            }
        }
    };    
	
	//Metodo de criacao
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_pareamento);
        
        //Inicia o adaptador
        adaptadorBT = BluetoothAdapter.getDefaultAdapter();        
        
        // Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);
        
        // Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	procuraAparelhos();
            }
        });        
    	
        // Initialize array adapters. One for already paired devices and one for newly discovered devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        
        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);        
        
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);        
        
        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = adaptadorBT.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } 
    }
    
    //Define mensagem de conectar aparelhos
    public void modalConectaAparelhos(String aparelho){
    	
		//Inicia modal e exibe
    	progressDialog = new ProgressDialog(this);
    	progressDialog.setIndeterminate(true);
    	progressDialog.setMessage("Trying to connect to\n" + aparelho);
    	progressDialog.show();     	
    }
    
    //Começa a procura de aparelhos
    public void procuraAparelhos(){
    	
		//Inicia modal e exibe
    	progressDialog = new ProgressDialog(this);
    	progressDialog.setIndeterminate(true);
    	progressDialog.setMessage("Searching for online phones...");
    	progressDialog.show();     	

        //Se o aparelho já esteja procurando, cancela
        if (adaptadorBT.isDiscovering()) {
        	adaptadorBT.cancelDiscovery();
        }

        //Inicia a procura
        adaptadorBT.startDiscovery();    	
    }  
    
    //Inicia ativiade de transicao de dados
    public void comecaMultiplayer(BluetoothSocket socket){
    	
    	//Insere o socket para a classe de transicao de objetos
    	TransitaAtributos.setSocketConexao(socket);
    	
    	//Define celular como servidor
    	TransitaAtributos.setPapelMultiplayer(MultiplayerGame.CLIENTE);    	
    	
    	//Declara intent, insere o socket e envia para a atividade
        Intent intent = new Intent(this, MultiplayerActivity.class);
        startActivityForResult(intent, ACT_MULTIPLAYER_GAME);    	    	
    }    
    
    //Finaliza a modal
    public void finalizaModal(boolean erro){
    	progressDialog.dismiss();
    	
    	//Exibe toast de erro no servidor
    	if(erro){
    		
            // Create the result Intent and include the MAC address
            Intent intent = new Intent();
            intent.putExtra(CONNECT_ERROR, aparelhoDescoberto.getName() + " " + aparelhoDescoberto.getAddress());

            // Set result and finish this Activity
            setResult(Activity.RESULT_FIRST_USER, intent);
            finish();
    	}
    }
}
