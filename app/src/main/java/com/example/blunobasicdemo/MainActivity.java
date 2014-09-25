package com.example.blunobasicdemo;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends BlunoLibrary {
    private Button buttonScan;
    private Button buttonSerialSend;
    private EditText serialSendText;
    private TextView serialReceivedText;
    private Spinner skinSpinner;
    private Spinner genderSpinner;
    private static final Integer[] skinTone = {R.drawable.blank,R.drawable.type_i,R.drawable.type_ii,R.drawable.type_iii,R.drawable.type_iv,R.drawable.type_v,R.drawable.type_vi};
    public final static String EXTRA_MESSAGE = "com.example.blunobasicdemo.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateProcess();	//onCreate Process by BlunoLibrary
        serialBegin(115200);	//set the Uart Baudrate on BLE chip to 115200
        serialReceivedText=(TextView) findViewById(R.id.serialReceivedText);	//initial the EditText of the received data
//serialSendText=(EditText) findViewById(R.id.serialSendText); //initial the EditText of the sending data
/*buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend); //initial the button for sending the data
buttonSerialSend.setOnClickListener(new OnClickListener() {
@Override
public void onClick(View v) {
// TODO Auto-generated method stub
serialSend(serialSendText.getText().toString()); //send the data to the BLUNO
}
});
buttonScan = (Button) findViewById(R.id.buttonScan); //initial the button for scanning the BLE device
buttonScan.setOnClickListener(new OnClickListener() {
@Override
public void onClick(View v) {
// TODO Auto-generated method stub
buttonScanOnClickProcess(); //Alert Dialog for selecting the BLE device
}
});*/
//test commit
//buttonScan = (Button) findViewById(R.id.buttonScan); //initial the button for scanning the BLE device
        addItemsToSkinSpinner();
        addItemsToGenderSpinner();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        if(mConnectionState.equals(connectionStateEnum.isNull)||mConnectionState.equals(connectionStateEnum.isToScan)){
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_scanning).setVisible(false);
            menu.findItem(R.id.menu_connecting).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
            menu.findItem(R.id.menu_disconnecting).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        }
        else if(mConnectionState.equals(connectionStateEnum.isScanning)) {
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_scanning).setVisible(true);
            menu.findItem(R.id.menu_connecting).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
            menu.findItem(R.id.menu_disconnecting).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        }
        else if(mConnectionState.equals(connectionStateEnum.isConnecting)) {
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_scanning).setVisible(false);
            menu.findItem(R.id.menu_connecting).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
            menu.findItem(R.id.menu_disconnecting).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        }
        else if(mConnectionState.equals(connectionStateEnum.isConnected)) {
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_scanning).setVisible(false);
            menu.findItem(R.id.menu_connecting).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
            menu.findItem(R.id.menu_disconnecting).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(null);

            Button buttonDisplayResults = (Button) findViewById(R.id.buttonDisplayResults);
            Button buttonSendBoth = (Button) findViewById(R.id.buttonSendBoth);
            buttonDisplayResults.setEnabled(true);
            buttonSendBoth.setEnabled(true);
        }
        else if(mConnectionState.equals(connectionStateEnum.isDisconnecting)) {
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_scanning).setVisible(false);
            menu.findItem(R.id.menu_connecting).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
            menu.findItem(R.id.menu_disconnecting).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                buttonScanOnClickProcess();
                break;
            case R.id.menu_scanning:
                buttonScanOnClickProcess();
                break;
            case R.id.menu_disconnect:
                buttonScanOnClickProcess();
        }
        return true;
    }
    /*
    public void scanDevice(View V) {
    buttonScanOnClickProcess(); //Alert Dialog for selecting the BLE device
    }
    public void sendData(View V) { //send the data to the BLUNO
    serialSend(serialSendText.getText().toString());
    }
    public void sendData1(View V) {
    serialSend("12");
    }
    */
    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();	//onResume Process by BlunoLibrary
    }
    /*
    public void addItemsToSkinSpinner() {
    skinSpinner = (Spinner) findViewById(R.id.skinSpinner);
    List<String> list = new ArrayList<String>();
    list.add("Please select the colour of your skin");
    list.add("Fair");
    list.add("Tanned");
    list.add("Very tanned");
    ArrayAdapter<String> skinDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
    skinDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    skinSpinner.setAdapter(skinDataAdapter);
    }*/
    public void addItemsToSkinSpinner() {
        skinSpinner = (Spinner) findViewById(R.id.skinSpinner);
        skinSpinner.setAdapter(new MySkinAdapter());
    }
    private static class SkinViewHolder {
        ImageView imageViewSkin;
    }
    private class MySkinAdapter extends BaseAdapter {
        public int getCount(){
            return skinTone.length;
        }
        @Override
        public Integer getItem(int position) {
            return skinTone[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            SkinViewHolder skinViewHolder;
//do we have a view?
            if(convertView == null) {
//we don't have a view so create one
                itemView = getLayoutInflater().inflate(R.layout.spinner_row,parent, false);
                skinViewHolder = new SkinViewHolder();
                skinViewHolder.imageViewSkin = (ImageView) itemView.findViewById(R.id.spinnerImage);
//set the tag for this view to the current image view holder
                itemView.setTag(skinViewHolder);
            }
            else {
//we have a view to get the tagged view
                skinViewHolder = (SkinViewHolder) itemView.getTag();
            }
//display the current image
            skinViewHolder.imageViewSkin.setImageDrawable(getResources().getDrawable(skinTone[position]));
            return itemView;
        }
    }
    public void addItemsToGenderSpinner() {
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        List<String> list = new ArrayList<String>();
        list.add("Please select your gender");
        list.add("Male");
        list.add("Female");
        ArrayAdapter<String> genderDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        genderDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderDataAdapter);
    }
    public void displayResults(View V) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        TextView textView = (TextView) findViewById(R.id.serialReceivedText);
        if(textView.getText().toString().equals("")){
            Toast.makeText(this,"Please select something", Toast.LENGTH_SHORT).show();
        }
        else {
            String message = textView.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }
    public void sendBoth(View V) {
        skinSpinner = (Spinner) findViewById(R.id.skinSpinner);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        if(skinSpinner.getSelectedItemPosition()!=0 && genderSpinner.getSelectedItemPosition()!=0) {
            serialReceivedText.getEditableText().clear();
            serialSend(String.valueOf(skinSpinner.getSelectedItemPosition())+String.valueOf(genderSpinner.getSelectedItemPosition()));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);	//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();	//onPause Process by BlunoLibrary
    }
    protected void onStop() {
        super.onStop();
        onStopProcess();	//onStop Process by BlunoLibrary
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();	//onDestroy Process by BlunoLibrary
    }
    /*@Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
    switch (theConnectionState) { //Four connection state
    case isConnected:
    buttonScan.setText("Connected");
    break;
    case isConnecting:
    buttonScan.setText("Connecting");
    break;
    case isToScan:
    buttonScan.setText("Scan");
    break;
    case isScanning:
    buttonScan.setText("Scanning");
    break;
    case isDisconnecting:
    buttonScan.setText("isDisconnecting");
    break;
    default:
    break;
    }
    }*/
    public Boolean isNumeric(String str) {
        try {
            float f = Float.parseFloat(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    @Override
    public void onSerialReceived(String theString) {	//Once connection data received, this function will be called
// TODO Auto-generated method stub
        serialReceivedText.append(theString);	//append the text into the EditText
//The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        if(isNumeric(theString)) {
            if (Float.parseFloat(theString) > 1) {
                Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
            }
        }
    }
}