package cn.app118.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JdomUtil {

	public static void main(String[] args) throws JDOMException, IOException {
		//File file = new File("F:\\develop\\app118\\src\\cn\\app118\\framework\\util\\text.xml");  
//		File file = new File("F:\\develop\\app118\\WebContent\\pages\\心知天气城市列表.xml");  
//		SAXBuilder builder = new SAXBuilder();  
//		Document doc = builder.build(file);  
//		parseCity(doc);// 解析XML文档
		parseCity();
	}
	
	
	// 解析XML文档
	private static void parseJDOM(Document doc) {
		Element root = doc.getRootElement();
		List lineList = root.getChildren("line");// 也可使用root.getChildren()
		for (Iterator iter = lineList.iterator(); iter.hasNext();) {
			Element lineElement = (Element) iter.next();// 获取<line>元素

			String lid = lineElement.getAttributeValue("lid");// 获取<line>元素的lid属性值
			String num = lineElement.getAttributeValue("num");// 获取<line>元素的num属性值

			System.out.println("==lid:" + lid);
			System.out.println("==num:" + num);

			Element idElement = lineElement.getChild("id");// 获得<line>下<id>标签下的子元素
			String id = idElement.getText();// // 获得<line>下<id>标签下的子元素值
			System.out.println("==路线id:" + id);

			List stationList = lineElement.getChildren("station");// 获得<line>下<station>列表
			for (Iterator subIter = stationList.iterator(); subIter.hasNext();) {
				Element stationElement = (Element) subIter.next();// 获取<station>元素

				Element sidElement = stationElement.getChild("sid");// 获得<station>下<sid>标签下的子元素
				Element snameElement = stationElement.getChild("sname");// 获得<station>下<sname>标签下的子元素
				String sid = sidElement.getText();// 获得<station>下<sid>标签下的子元素值
				String sname = snameElement.getText();// 获得<station>下<sname>标签下的子元素值

				System.out.println("==路线sid:" + sid);
				System.out.println("==路线sname:" + sname);
			}

		}
	}
	
	public static Set parseCity() throws JDOMException, IOException {
		File file = new File("F:\\develop\\app118\\WebContent\\pages\\心知天气城市列表.xml");  
		SAXBuilder builder = new SAXBuilder();  
		Document doc = builder.build(file);  
		Element root = doc.getRootElement();
		List<Element> areaList = root.getChildren("area");
		Set<String> set=new HashSet<String>(); 
		int i=1;
	    for(Element e:areaList){
	    	List<Element> areaList2 =e.getChildren("area");
	    	//System.out.println("***"+areaList2.size());
	    	for(Element m:areaList2){
	    		List<Element> areaList3 =m.getChildren("area");
	    		//System.out.println("###"+areaList3.size());
	    		for(Element t:areaList3){
	    			Element city=t.getChild("city");
	    			Attribute attribute=city.getAttribute("name");
	    			set.add(attribute.getValue());
	    			//System.out.println((i++)+":"+attribute.getValue());
	    		}
	    	}
	    }
	    
	    for(String s:set){
	    	//System.out.println(s);
	    }
	    System.out.println(set.size());
		return set;
	}
}
