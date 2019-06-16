
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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


public class SubjectClass {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration config = new Configuration();
    String[] otherArgs = new GenericOptionsParser(config, args).getRemainingArgs();
    if (otherArgs.length < 2) {
      System.err.println("Usage: wordcount <in> [<in>...] <out>");
      System.exit(2);
    }
        
		System.out.println("in here");
        Job job = Job.getInstance(config);
        
        job.setJarByClass(SubjectClass.class);
        
        
        job.setMapperClass(myMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        
        
        job.setReducerClass(myReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        
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
				String temp1 = ",";
				context.write(new Text(words[2]), new Text(words[0]+temp1+words[4]));
            }
        }

        public static class myReducer extends Reducer<Text, Text, Text, Text> {
            // 实现reducer函数
            public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
               
			   int [][] ClassScore = new int[1000000][3];
			   int classid = 0;
               for (Text value : values) {
                String words[]=value.toString().split(",");
				int len = words[0].length();
				classid = Integer.valueOf(words[0].substring(0, len-1));
				ClassScore[classid-160000][0]++;
				ClassScore[classid-160000][1] += Integer.valueOf(words[1]); 
                
            }
			for(int j=0; j<1000000; j++){
				
				if(ClassScore[j][0]>0){
					double sum = 0.0;
					int num = 0;
					sum = (double)ClassScore[j][1]/ClassScore[j][0];
					context.write(key, new Text(String.valueOf(j+160000)+"班,"+String.valueOf(sum)));
				}
			}
			
			
            
        }
    } 
}