package com.example.testingrestdocs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HomeController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void postMessageTest() throws Exception {
		ConstrainedFields constrainedFields = new ConstrainedFields(Message.class);
		
		this.mockMvc
				.perform(post("/message").content("{\"sender\":\"Marc\",\"content\":\"HI\"}")
						.characterEncoding("utf-8")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andDo(document("home-message", requestFields(
						attributes(key("title").value("Request fields:")),
						constrainedFields.withPath("sender").description("Sender of the message"),
						constrainedFields.withPath("content").description("Content of the message")
						)));
	}

	private static class ConstrainedFields {
		private final ConstraintDescriptions constraintDescriptions;
		
		ConstrainedFields(Class<?> input) {
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}
		
		private FieldDescriptor withPath(String property) {
			return fieldWithPath(property).attributes(key("constraints").value(
					// Let's assume there is only one constraint for each property
					constraintDescriptions.descriptionsForProperty(property).get(0)));
		}
	}
}
