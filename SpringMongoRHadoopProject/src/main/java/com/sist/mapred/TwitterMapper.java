package com.sist.mapred;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TwitterMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	private final IntWritable one=new IntWritable(1);
	private Text result=new Text();
	private String[] data={"문재인","안철수","안희정","이재명","홍준표"};
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
			Pattern[] p=new Pattern[5];
			for(int i=0;i<p.length;i++){
				p[i]=Pattern.compile(data[i]);
			}
			// 긍정 => 10개, 부정 => 10개 => 0~9사이에 있으면 긍정이라고key를 주면 됌 / 10~19사이에 있으면 부정이라고key를 주면 됌
			
			Matcher[] m=new Matcher[5];
			for(int i=0;i<m.length;i++){
				m[i]=p[i].matcher(value.toString());
				if(m[i].find()){
					result.set(data[i]);
					context.write(result, one);
				}
			}
	}
	
}
