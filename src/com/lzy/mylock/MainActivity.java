package com.lzy.mylock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	
	private DevicePolicyManager policyManager;
	private ComponentName componentName;
	private static final int MY_REQUEST_CODE = 9999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //��ȡ�豸�������
      		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
      		componentName = new ComponentName(this, AdminReceiver.class);
      		/*
      		* �������ж��Ƿ���Ȩ�ޣ����û�������activeManage()��Ȼ��������������finish()��
      		* ��������������ģ���ΪactiveManage()���ܻ��ڵȴ���һ��Activity�Ľ������ô��ʱ��Ȼû��Ȩ��ȴ
      		* ִ����lockNow()�������ͳ����ˡ�
      		* ��������2����
      		* 1������дOnActivityResult()�������������ж��Ƿ��ȡȨ�޳ɹ�������������finish()
      		* �����������activeManage()��ȡȨ�ޣ��������������������Ч���ܺã�
      		* 2������дOnActivityResult()��������һ�λ�ȡȨ�޺�����������finish()��������������˵Ҳ����
      		* ʧ�ܣ�����Ȩ�޻�û��ȡ�þ�finish�ˣ����������ͻص����棬�����ٰ�һ��������������
      		* �����Ƽ���һ�ַ�����*/

      		//�ж��Ƿ�������Ȩ�ޣ����������������������Լ�����û�����ȡȨ��
      		if (policyManager.isAdminActive(componentName))
      		{
      			policyManager.lockNow();
      			finish();
      		}
      		else
      		{
      			activeManage();
      		}
      		setContentView(R.layout.activity_main); //���������������������ʱ��Ͳ�������������һ�£�

    }
    
  //��ȡȨ��
  	private void activeManage()
  	{
  		// �����豸����(��ʽIntent) - ��AndroidManifest.xml���趨��Ӧ������
  		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

  		//Ȩ���б�
  		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

  		//����(additional explanation)
  		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "��������ʹ����������Ŷ��^^");

  		startActivityForResult(intent, MY_REQUEST_CODE);
  	}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//��ȡȨ�޳ɹ�������������finish�Լ������������ȡȨ��
		if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
		{
			policyManager.lockNow();
			finish();
		}
		else
		{
			activeManage();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
