package org.ma.face;

import java.util.List;
import java.util.Map;

public interface ShowResult {
	
	void appendResultN(String str);

	void appendDiffMap(Map<String, List<String>> diff);
}
