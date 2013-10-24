package com.Qysoft.Buy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.com.Qysoft.WebService.retrieveData;

public class test extends Activity {
	 EditText editText;
	 Button button;
    Button btnQuery;
    ListView listView;
    ListView listViewTitle;
    Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testmain);
        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        editText = (EditText)this.findViewById(R.id.editText);
		button = (Button)this.findViewById(R.id.button1);
        btnQuery = (Button)this.findViewById(R.id.btnQuery);
        listView = (ListView)this.findViewById(R.id.listView);
        listViewTitle = (ListView)this.findViewById(R.id.listViewTitle);
        spinner = (Spinner)findViewById(R.id.spinner);
		button.setOnClickListener(new Button.OnClickListener() {
			//@Override
			public void onClick(View v) {
				click();
			}

		});
        btnQuery.setOnClickListener(new Button.OnClickListener() {
            //@Override
            public void onClick(View v) {
                onClickQuery(v);
            }

        });
		Intent intent = this.getIntent();
		ArrayList<String> list =  intent.getStringArrayListExtra("detail");
        initView();
	}

    private void initView() {
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> hmWLbh = new HashMap<String, String>();
        hmWLbh.put("tvWlbh", "物料编号");
        hmWLbh.put("tvWlmc", "物料名称");
        hmWLbh.put("tvWlxhgg", "型号规格");
        hmWLbh.put("tvSl", "数量");
        hmWLbh.put("tvCgdj", "采购单价");
        hmWLbh.put("tvBz", "备注");
        list.add(hmWLbh);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item,new String[]{"tvWlbh","tvWlmc","tvWlxhgg","tvSl","tvCgdj","tvBz"},new int[]{R.id.tvWlbh,R.id.tvWlmc,R.id.tvWlxhgg,R.id.tvSl,R.id.tvCgdj,R.id.tvBz});
        listViewTitle.setAdapter(simpleAdapter);

        List<String> listForSpin = new ArrayList<String>();
        listForSpin.add("测试1");
        listForSpin.add("测试2");
        listForSpin.add("测试3");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listForSpin);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
Toast.makeText(test.this,"xxxxxxxxxxxxx",1).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void click()
	{
		Intent intent = new Intent();
		intent.setClass(test.this, com.zijunlin.Zxing.Demo.CaptureActivity.class);
		//ArrayList<String> arrayList = new ArrayList<String>();
		//arrayList.add(str);
		//intent.putExtra("detail", arrayList);
		startActivityForResult(intent, 0);
	}
    void onClickQuery(View v)
    {
        String s = spinner.getSelectedItem().toString();
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        String cghth = editText.getText().toString();
        String sqlText = "select a.wlbh,a.wlmc,a.wlxhgg,a.cgsl,a.cgdj,a.bz  " +
                        "from cg_cghtqd  a inner join cg_cghtzy b on a.cghtzyid = b.cghtzyid where b.cghth='"+cghth+"'";
        List<List<String>> listData = retrieveData.RetrieveDetail(sqlText);
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        if(listData == null) return;
        for (int i=0;i<listData.size();i++)
        {
            pushItem(list,listData.get(i));
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item,new String[]{"tvWlbh","tvWlmc","tvWlxhgg","tvSl","tvCgdj","tvBz"},new int[]{R.id.tvWlbh,R.id.tvWlmc,R.id.tvWlxhgg,R.id.tvSl,R.id.tvCgdj,R.id.tvBz});
        listView.setAdapter(simpleAdapter);
    }
    void  pushItem( List<HashMap<String,String>> list,List<String> items)
    {
        HashMap<String,String> hmWLbh = new HashMap<String, String>();
        hmWLbh.put("tvWlbh", items.get(0) );
        hmWLbh.put("tvWlmc",items.get(1));
        hmWLbh.put("tvWlxhgg", items.get(2));
        hmWLbh.put("tvSl", items.get(3));
        hmWLbh.put("tvCgdj", items.get(4));
        hmWLbh.put("tvBz", items.get(5));
        list.add(hmWLbh);
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
		//返回的条形码数据
        if(data == null) return;
		String code = data.getStringExtra("Code");
	
		//输入文本框
        editText.setText(code);

	}

}
