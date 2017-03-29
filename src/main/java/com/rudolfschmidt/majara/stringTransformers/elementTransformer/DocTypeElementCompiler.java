package com.rudolfschmidt.majara.stringTransformers.elementTransformer;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.HashMap;
import java.util.Map;

class DocTypeElementCompiler {

	private static final Map<String, String> DOCTYPES;
	private static final String DOCTYPE = "doctype";

	static {
		DOCTYPES = new HashMap<>();
		DOCTYPES.put("html", "<!DOCTYPE html>");
		DOCTYPES.put("xml", "<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		DOCTYPES.put("transitional", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		DOCTYPES.put("strict", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		DOCTYPES.put("frameset", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">");
		DOCTYPES.put("1.1", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		DOCTYPES.put("basic", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\" \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">");
		DOCTYPES.put("mobile", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
		DOCTYPES.put("plist", "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
	}

	static boolean compile(Node node, StringBuilder sb, boolean pretty) {
		if (node.getType() != NodeType.ELEMENT_NAME) {
			return false;
		}
		if (!node.getValue().equals(DOCTYPE)) {
			return false;
		}
		final String doctype = DOCTYPES.get(node.getNodes().getFirst().getValue());
		if (doctype == null) {
			throw new IllegalArgumentException("Unknown DocType");
		}
		sb.append(doctype);
		if (pretty) {
			sb.append("\n");
		}
		return true;
	}
}
