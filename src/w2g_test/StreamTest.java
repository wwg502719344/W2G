package w2g_test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by W2G on 2020/9/20.
 * java8 新特性 测试开发
 * https://www.cnblogs.com/wuhenzhidu/p/10740091.html
 */
public class StreamTest {



    public static void main(String[] args){
        List<Person> personList = new ArrayList<Person>();
        init(personList);

        //普通筛选条件进行输出
        //personList.stream().filter((p) -> p.getAge() > 21).forEach(System.out::println);
        //long chinaPersonNum = personList.stream().filter((p) -> p.getCountry().equals("中国")).count();
        //System.out.println(chinaPersonNum);

        //筛选条件组成新的list列表
        //List<Person> a= personList.stream().filter((p) -> p.getSex() == 'F').limit(2).collect(Collectors.toList());
        //System.out.println(a.size());

        List array=new ArrayList();
        List a=personList.stream().map((p) -> {
            Map personmap = new HashMap<>();
            personmap.put("key1",p.getCountry());
            array.add(personmap);
            return array;
        }).collect(Collectors.toList());

        System.out.println(array.size());
    }

    private static void init(List personList) {

        personList.add(new Person("欧阳雪",18,"中国",'F'));
        personList.add(new Person("Tom",24,"美国",'M'));
        personList.add(new Person("Harley",22,"英国",'F'));
        personList.add(new Person("向天笑",20,"中国",'M'));
        personList.add(new Person("李康",22,"中国",'M'));
        personList.add(new Person("小梅",20,"中国",'F'));
        personList.add(new Person("何雪",21,"中国",'F'));
        personList.add(new Person("李康",22,"中国",'M'));
    }
}
