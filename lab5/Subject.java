
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class Subject {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration config = new Configuration();
    String[] otherArgs = new GenericOptionsParser(config, args).getRemainingArgs();
    if (otherArgs.length < 2) {
      System.err.println("Usage: wordcount <in> [<in>...] <out>");
      System.exit(2);
    }
        
		System.out.println("in here");
        Job job = Job.getInstance(config);
        
        job.setJarByClass(Subject.class);
        
        //设置所用到的map类
        job.setMapperClass(myMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        
        //设置用到的reducer类
        job.setReducerClass(myReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        //设置输入输出地址
       for (int i = 0; i < otherArgs.length - 1; ++i) {
      FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
    }
    FileOutputFormat.setOutputPath(job,
      new Path(otherArgs[otherArgs.length - 1]));
        
        
        
        //指定文件的输出地址
        
        //启动处理任务job
        boolean completion = job.waitForCompletion(true);
        if(completion){
            System.out.println("Job Success!");
        }
        
    }
    
     public static class myMapper extends Mapper<Object, Text, Text, Text> {
            // 实现map函数
            public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
                //String temp=new String();// 左右表标识
                
                String values=value.toString();
                String words[]=values.split(",");
                String temp = new String("必修");
                if(temp.equals(words[3])){
					context.write(new Text(words[1]), new Text(words[4]));
				}
                
                //(Lucy,1+Tom+Lucy)
 
               
            }
        }

        public static class myReducer extends Reducer<Text, Text, Text, Text> {
            // 实现reducer函数
            public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
               
               double sum = 0.0;
			   int num = 0;
			   //String temp = new String("丁子怡");
               for (Text value : values) {
                sum += Integer.valueOf(value.toString());
				num++;
				//if(temp.equals(key.toString())){
				//	System.out.println(Integer.valueOf(value.toString()));
				//}
                
            }
			sum = sum/num;
			context.write(key, new Text(String.valueOf(sum)));
            
        }
    } 
}