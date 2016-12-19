import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.pay.poss.base.tool.ReflectUtils;
import com.pay.poss.security.util.IpUtils;
import com.pay.poss.util.IpayUtil;
import com.pay.poss.util.JsonUtil;
import com.pay.util.JSonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by songyilin on 2016/10/13 0013.
 */
public class Test {

    public static final ImmutableMap<String, String> map = new ImmutableMap.Builder<String, String>().put("name", "姓名").put("age", "年龄").build();

    public static void main(String[] args) throws Exception{
        /*Boy boy = new Boy();
        boy.setAge("1");
        boy.setName("2");
        Boy boy1 = new Boy();
        boy1.setAge("3");
        boy1.setName("4");

       *//* Map<String, Object> resMap = Maps.newHashMap();
        Field[] fields = boy.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Object obj = field.get(boy);
            String fieldName = field.getName();
            resMap.put(map.get(fieldName), obj);
            System.out.println(resMap);
        }
        *//*
        List<Boy> list = Lists.newArrayList();

        ArrayList<Map<String, Object>> resList = (ArrayList<Map<String, Object>>) JsonUtil.json2Entity(JsonUtil.entity2Json(list), ArrayList.class);

        System.out.println(resList);*/

        int i = RandomUtils.nextInt(new Random(System.currentTimeMillis()), 9999);
        System.out.println(i);
    }
}
