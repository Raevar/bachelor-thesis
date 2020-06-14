docugen 0.3
***********

docugen is a simple program that converts HTML files with additional tags and attributes to standard HTML files.
The additional tags and attributes are:

<toc/>
	Creates a table of contents which contains chapters, sections and subsections and hyperlinks to them.

<chapter id="optional">Title</chapter>
	Begin of a new chapter with the given title. ID is optional.
	
<section id="optional">Title</section>
	Begin of a new section with the given title. ID is optional.
	
<subsection id="optional">Title</subsection>
	Begin of a new subsection with the given title. ID is optional.
	
<subsubsection id="optional">Title</subsubsection>
	Begin of a new subsubsection with the given title. ID is optional.
	
<javacode src="mycode.java" from="5" to="15"/>
  Insert Java code from the file mycode.java, but only lines 5 to 15 ("from" and "to" is optional).
  
<xmlcode src="mycode.xml" from="5" to="15"/>
  Insert XML code from the file mycode.xml, but only lines 5 to 15 ("from" and "to" is optional).
  
<ref to="some_id"/>
  Replaced by a link to the chapter/section/subsection with the given ID
  
  
---

Andreas Wenger
2009-08
