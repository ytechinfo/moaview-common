import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pub.core.util.JsonUtils;

public class Test {
	
	@JsonFormat(shape= JsonFormat.Shape.OBJECT)
	enum MetadataTab{
		COLUMN("Column","column"), DDL("DDL", "ddl");
		
		private String name;
		private String tabid;
		
		MetadataTab(String name , String tabid){
			this.name = name; 
			this.tabid = tabid; 
		}
		
		public String getName() {
			return this.name;
		}
		
		public String getTabid() {
			return this.tabid;
		}
	}
	
	public static void main(String[] args) {
		
		List<MetadataTab> aaa = new ArrayList<MetadataTab>();
		
		aaa.add(MetadataTab.COLUMN);
		aaa.add(MetadataTab.DDL);
		
		
		System.out.println(JsonUtils.objectToString(aaa));
	}
}
