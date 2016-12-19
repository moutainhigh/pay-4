//package gui.ava.html.parser.parser;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import gui.ava.html.BaseTest;
//import gui.ava.html.parser.HtmlParserImpl;
//
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.net.URL;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.util.ResourceUtils;
//import org.w3c.dom.Document;
//
///**
// * @author PengJiangbo
// */
//public class HtmlParserImplTest extends BaseTest {
//	private HtmlParserImpl parser;
//
//	@Before
//	public void createParser() {
//		parser = new HtmlParserImpl();
//	}
//
//	@Test
//	public void testLoadURI() throws Exception {
//		parser.load(getTest1Url().toURI());
//		assertTest1();
//	}
//
//	@Test
//	public void testLoadExternalURL() throws Exception {
//		parser.load(new URL("http://www.google.co.il"));
//		assertTrue(getDocument().getElementsByTagName("div").getLength() > 0);
//	}
//
//	@Test
//	public void testLoadFile() throws Exception {
//		parser.load(ResourceUtils.getFile(TEST1_PATH));
//		assertTest1();
//	}
//
//	@Test
//	public void testLoadReader() throws Exception {
//		parser.load(new FileReader(ResourceUtils.getFile(TEST1_PATH)));
//		assertTest1();
//	}
//
//	@Test
//	public void testLoadInputStream() throws Exception {
//		parser.load(new FileInputStream(ResourceUtils.getFile(TEST1_PATH)));
//		assertTest1();
//	}
//
//	@Test
//	public void testLoadHtml() throws Exception {
//		parser.loadHtml("<b>Hello</b>");
//		assertEquals(getDocument().getElementsByTagName("b").item(0).getTextContent(), "Hello");
//	}
//
//	private void assertTest1() {
//		assertEquals(getDocument().getElementsByTagName("strong").item(0).getTextContent(), "Hello");
//	}
//
//	private Document getDocument() {
//		return parser.getDocument();
//	}
//}
