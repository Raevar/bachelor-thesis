package docugen;

import java.io.File;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * This class does transforms a given docugen-extended XHTML document
 * to a standard XHTML document.
 * 
 * This is destructive, i.e. the given document is modified.
 * 
 * @author Andreas Wenger
 */
public class Factory
{
	
	private Document doc;
	private File parentDir;
	
	private Tree toc = new Tree();
	private Tree currentNode = toc;
	
	private static Hashtable<String, Integer> sectionLevel = new Hashtable<String, Integer>();
	private static Hashtable<String, String> sectionElements = new Hashtable<String, String>();
	private static Hashtable<String, Tree> sectionIDs = new Hashtable<String, Tree>();
	
	
	static
	{
		sectionLevel.put("chapter", 1);
		sectionLevel.put("section", 2);
		sectionLevel.put("subsection", 3);
		sectionLevel.put("subsubsection", 4);
		sectionElements.put("chapter", "h2");
		sectionElements.put("section", "h3");
		sectionElements.put("subsection", "h4");
		sectionElements.put("subsubsection", "h4");
	}
	
	
	public Factory(Document doc, File parentDir)
	{
		this.doc = doc;
		this.parentDir = parentDir;
		transform();
	}
	

	private void transform()
	{
		//transform elements
		Element root = XMLReader.root(doc);
		for (Element e : XMLReader.elements(root))
		{
			transform(root, e);
		}
		//now, search again for toc elements and place TOC there
		for (Element e : XMLReader.elements(root))
		{
			writeTOC(root, e);
		}
		//search again for ref elements and replace them by hyperlinks
		for (Element e : XMLReader.elements(root))
		{
			transformRefs(root);
		}
	}
	
	
	private void transform(Element parent, Element element)
	{
		//docugen element? then replace it
		String name = element.getTagName();
		if (name.equals("chapter") || name.equals("section") ||
			name.equals("subsection")  || name.equals("subsubsection"))
		{
			//chapter, section or subsection
			String title = element.getTextContent();
			moveTOCtoLevel(sectionLevel.get(name) - 1);
			currentNode = currentNode.addChild(title);
			Element eH = doc.createElement(sectionElements.get(name));
			eH.setTextContent(currentNode.getLevelsAsString() + " " + title);
			parent.replaceChild(eH, element);
			Element eA = doc.createElement("a");
			eA.setAttribute("name", currentNode.getLevelsAsString());
			parent.insertBefore(eA, eH);
			//ID for references
			String id = element.getAttribute("id");
			if (id.length() > 0)
			{
				sectionIDs.put(id, currentNode);
			}
		}
		else if (name.equals("javacode") || name.equals("xmlcode"))
		{
			//java code
			String src = element.getAttribute("src");
			String from = element.getAttribute("from");
			String to = element.getAttribute("to");
			if (src.length() == 0)
			{
				Main.errFatal("javacode/xmlcode element without src");
			}
			File srcFile = new File(parentDir, src);
			if (!srcFile.exists())
			{
				Main.errFatal("file " + srcFile + " not found!");
			}
			Integer fromInt = (from.length() > 0 ? Integer.parseInt(from) : null);
			Integer toInt = (from.length() > 0 ? Integer.parseInt(to) : null);
			String text = null;
			try
			{
				text = FileReader.read(srcFile, fromInt, toInt);
			}
			catch (Exception ex)
			{
				Main.errFatal("error reading file " + srcFile);
			}
			Element ePre = doc.createElement("pre");
			String classAttr = "brush: java;";
			if (name.equals("xmlcode"))
			{
				classAttr = "brush: xml;";
			}
			text.replaceAll("<", "&lt;");
			ePre.setTextContent(text);
			if (fromInt != null)
				classAttr += "first-line: " + fromInt + ";";
			ePre.setAttribute("class", classAttr);
			parent.replaceChild(ePre, element);
		}
		else
		{
			//no docugen element. recursive check for child elements
			for (Element e : XMLReader.elements(element))
			{
				transform(element, e);
			}
		}
	}
	
	
	private void moveTOCtoLevel(int level)
	{
		while (level < currentNode.getLevel())
		{
			currentNode = currentNode.getParent();
		}
	}
	
	
	private void writeTOC(Element parent, Element element)
	{
		//toc element? then replace it with TOC
		String name = element.getTagName();
		if (name.equals("toc"))
		{
			//write TOC there
			parent.replaceChild(createTOC(), element);
		}
		else
		{
			//no toc element. recursive check for child elements
			for (Element e : XMLReader.elements(element))
			{
				writeTOC(element, e);
			}
		}
	}
	
	
	public Document getDocument()
	{
		return doc;
	}
	
	
	private Element createTOC()
	{
		Element eTOC = doc.createElement("ul");
		eTOC.setAttribute("style", "list-style-type:none");
		for (Tree child : toc.getChildren())
		{
			createTOC(eTOC, child);
		}
		return eTOC;
	}
	
	
	private void createTOC(Element builder, Tree node)
	{
		//this node
		Element eA = doc.createElement("a");
		eA.setAttribute("href", "#" + node.getLevelsAsString());
		Element eH = doc.createElement("li");
		eH.setTextContent(node.getLevelsAsString() + " " + node.getTitle());
		eA.appendChild(eH);
		builder.appendChild(eA);
		//children
		if (node.getChildren().size() > 0)
		{
			Element builderChildren = doc.createElement("ul");
			builderChildren.setAttribute("style", "list-style-type:none");
			builder.appendChild(builderChildren);
			for (Tree child : node.getChildren())
			{
				createTOC(builderChildren, child);
			}
		}
	}
	
	
	private void transformRefs(Element parent)
	{
		for (Element e : XMLReader.elements(parent))
		{
			if (e.getTagName().equals("ref"))
			{
				String to = e.getAttribute("to");
				if (to.length() == 0)
				{
					Main.errFatal("ref without to-attribute");
				}
				else
				{
					Tree section = sectionIDs.get(to);
					if (section == null)
					{
						Main.errFatal("ref to unknown ID: " + to);
					}
					else
					{
						Element eA = doc.createElement("a");
						eA.setAttribute("href", "#" + section.getLevelsAsString());
						eA.setTextContent(section.getLevelsAsString());
						parent.replaceChild(eA, e);
					}
				}
			}
			else
			{
				transformRefs(e);
			}
		}
	}
	
	
}