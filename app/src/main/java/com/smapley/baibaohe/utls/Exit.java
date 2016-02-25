package com.smapley.baibaohe.utls;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

import java.util.LinkedList;
import java.util.List;

public class Exit extends Application {
	
	private List<Activity> activityList=new LinkedList<Activity>();
	private static Exit instance;
	//����ģʽ�л�ȡΨһ��ExitApplication ʵ��
	public static Exit getInstance()
	{
		if(null == instance)
		{
			instance = new Exit();
		}
		return instance;

	}
	
	//����Activity ��������
	public void addActivity(Activity activity)
	{
		activityList.add(activity);
	}
	
	//��������Activity ��finish
	public void exit()
	{
		for(Activity activity:activityList)
		{
			activity.finish();
		}
		System.exit(0);
	}

	
	public void exitAlert (AlertDialog.Builder builder){
		builder.setMessage("ȷ��Ҫ�˳�SpareTime��");
		builder.setPositiveButton("��",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Exit.getInstance().exit();
			}
		});	
		builder.setNegativeButton("��",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});		
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	// ��ʾ�Ի���
	public void infoAlert(AlertDialog.Builder builder,String msg){
		builder.setMessage(msg)
				   .setCancelable(false)
				       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}	
}