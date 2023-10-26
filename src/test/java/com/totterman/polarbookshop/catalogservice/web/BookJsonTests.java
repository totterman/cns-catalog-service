package com.totterman.polarbookshop.catalogservice.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.totterman.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JsonTest
class BookJsonTests {

    @Autowired
    private JacksonTester<Book> bookJacksonTester;

    @Test
    void testSerialize() throws Exception {
        var now = Instant.now();
        var book = new Book(394L, "1234567890", "Title", "Author",
                9.90, "Polarsophia", now, now,
                "Foo Bar" , "Bar Foo",21);
        var jsonContent = bookJacksonTester.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher")
                .isEqualTo(book.publisher());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate")
                        .isEqualTo(book.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate")
                .isEqualTo(book.lastModifiedDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdBy")
                .isEqualTo(book.createdBy());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedBy")
                .isEqualTo(book.lastModifiedBy());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version")
                .isEqualTo(book.version());
    }

    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2023-10-10T12:52:12.123456Z");
        var content = """
                {
                "id": 394,
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90,
                "publisher": "Polarsophia",
                "createdDate": "2023-10-10T12:52:12.123456Z",
                "lastModifiedDate": "2023-10-10T12:52:12.123456Z",
                "createdBy": "Foo Bar",
                "lastModifiedBy": "Bar Foo",
                "version": 21
                }
                """;
        assertThat(bookJacksonTester.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(394L, "1234567890", "Title",
                        "Author", 9.90, "Polarsophia", instant,
                        instant, "Foo Bar",  "Bar Foo", 21));
    }
}
