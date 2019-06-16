<div style = "font-size: 2em;"><center >用Hadoop的框架下运行MapReduce算法</center></div>



# 运行的过程



## Map

输入数据可以包含若干个文件，每个文件有若干行，每一行都是一个记录。我们在`Map`的阶段的时候，就是要将这些记录映射为`<key, value>`的键值对，`value`值可以有多个值。`Map`过程的时候将相同`key`的键值对合并。



## Reduce

对于前面的每一个前面处理好的`key`进行处理，后面的`value`会有多个。



# 实验内容

## 实验一

统计每一个学生的必修课的平均成绩。



### 思路

`Map`阶段将每一个同学的名字作为`key`，然后判断每一条成绩看是否是必修，是的话就加入到键值对中。

`Reduce`阶段对于每一个key我们有若干的必修课的成绩，我们进行求和求平均值就可以了。



## 实验二

求每一门科目，按照班级划分，求每个班的平均分



### 思路

`Map`阶段将每一门课名字作为`key`，然后将班级号和成绩加入到`value`中。

`Reduce`阶段先读取班级号，然后将成绩加入到不同的班级中，最后求出每一个班级对于这一门课的平均成绩。



## 实验三

有若干的`child-parent`的关系对

求`child-grandparent`的关系对



### 思路

`Map`阶段将`parent`值作为`key`，`value`为`‘son’,son, parent`集合中。

将`son`值作为`key`，`value`为`‘grandparent’,son, parent`集合中。



`Reduce`阶段求出该`key`的`child`和`grandparent`，然后做笛卡尔乘积，从而求出相应的关系。



# 遇到的问题及解决方法

1. 运行最后一步`hadoop jar mrtest,jar WordCount /input /output`的时候，会报无法找到路径的错误。

解决：是因为windows是中文用户名的原因，然后切换到administrator模式就好了。



# 实验的结果

![](E:\16030130096 田宝林\大三下\7-分布式系统作业\homework5\1.png)

<div><center>图1 每个学生必修平均成绩</center></div>

![](E:\16030130096 田宝林\大三下\7-分布式系统作业\homework5\2.png)

<div><center>图2 每门课按班级的平均分</center></div>

![](E:\16030130096 田宝林\大三下\7-分布式系统作业\homework5\3.png)

<div><center>图3 child-grandparent关系图</center></div>



# 心得体会

熟悉了在Hadoop的框架下进行分布式的编程，发现`Mapreduce`的过程没有想象中的那么复杂，能够解决的分布式的问题也是有限的。

# 附录

### Exp1 `Subject.java`

``` java

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
```



### Exp2 `SubjectClass.java`

``` java

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
```



### Exp3 `TestParents.java`

``` java

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


public class TestParents {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration config = new Configuration();
    String[] otherArgs = new GenericOptionsParser(config, args).getRemainingArgs();
    if (otherArgs.length < 2) {
      System.err.println("Usage: wordcount <in> [<in>...] <out>");
      System.exit(2);
    }
        
		System.out.println("in here");
        Job job = Job.getInstance(config);
        
        job.setJarByClass(TestParents.class);
        
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
        boolean completion = job.waitForCompletion(true);
        if(completion){
            System.out.println("Job Success!");
        }
        
    }
    
     public static class myMapper extends Mapper<Object, Text, Text, Text> {
            // 实现map函数
            public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
                String temp=new String();
                String values=value.toString();
                String words[]=values.split(",");
                temp = "son";
                context.write(new Text(words[1]), new Text(temp +","+ words[0] + "," + words[1]));
                temp = "grand";
                context.write(new Text(words[0]), new Text(temp +","+ words[0] + "," + words[1]));
                
            }
        }

        public static class myReducer extends Reducer<Text, Text, Text, Text> {
            // 实现reducer函数
            public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
               List<String> grandchild = new ArrayList<String>();
               List<String> grandparent = new ArrayList<String>();
               
               for (Text value : values) {
                
                String words[]=value.toString().split(",");
                String temp = words[0];
                
                if(temp.equals("son")){
                    grandchild.add(words[1]);
                }
                
                if(temp.equals("grand")){
                    grandparent.add(words[2]);
                }
            }
               for (String gson : grandchild) {
                for (String gpar : grandparent) {
                    context.write(new Text(gson),new Text(gpar));
                }
            }
        }
    } 
}
```

