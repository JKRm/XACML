package org.cas.iie.xp.maptype;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;


import org.cas.iie.xp.maptype.StringLong.Entry;


//注意更改继承抽象类的泛型的类型
//该转换器负责完成StringCat与Map<String, Cat>的相互转换
public class XmlMapAdapter extends XmlAdapter<StringLong, Map<String,Long>> {

	@Override
	public Map<String,Long> unmarshal(StringLong v) throws Exception {
		
		Map<String,Long> result = new HashMap<String,Long>();
		
		for(Entry entry : v.getEntries()){
			
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}

	@Override
	public StringLong marshal(Map<String,Long> v) throws Exception {
		
		StringLong sc = new StringLong();
		
		for(String key : v.keySet()){
			
			sc.getEntries().add(new Entry(key, v.get(key)) );
		}
		
		return sc;
	}

}
