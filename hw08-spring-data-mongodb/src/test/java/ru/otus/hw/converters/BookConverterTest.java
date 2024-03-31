package ru.otus.hw.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Конвертер для работы с книгами должен")
@SpringBootTest(classes = {BookConverter.class})
public class BookConverterTest {
    private static final String FIRST_AUTHOR_ID = "a1";

    private static final String FIRST_GENRE_ID = "g1";

    private static final String SECOND_GENRE_ID = "g2";

    private static final String FIRST_BOOK_ID = "b1";

    @Autowired
    private BookConverter bookConverter;

    @MockBean
    private AuthorConverter authorConverter;

    @MockBean
    private GenreConverter genreConverter;
    private Author author;

    private List<Genre> genres;
    private Book book;
    private AuthorDto authorDto;

    private List<GenreDto> genreDtos;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        author = getAuthor();
        genres = getGenres();
        book = getBook(author, genres);
        authorDto = getAuthorDto();
        genreDtos = getGenreDtos();
        bookDto = getBookDto(authorDto, genreDtos);
    }

    @DisplayName("корректно преобразовывать DTO в строку. текущий метод modelToString(BookDto book)")
    @Test
    void modelToStringTest() {

        String expectedBook = "Id: %s, title: BookTitle_1,".formatted(FIRST_BOOK_ID) +
                " author: {Id: %s, FullName: Author_1},".formatted(FIRST_AUTHOR_ID) +
                " genres: [{Id: %s, Name: Genre_1}, {Id: %s, Name: Genre_2}]".formatted(FIRST_GENRE_ID, SECOND_GENRE_ID);
        when(authorConverter.dtoToString(authorDto)).thenReturn("Id: %s, FullName: Author_1".formatted(FIRST_AUTHOR_ID));
        for (int i = 0; i < genreDtos.size(); i++) {
            when(genreConverter.dtoToString(genreDtos.get(i)))
                    .thenReturn("Id: g%s, Name: Genre_%s".formatted(i + 1, i + 1));
        }

        String actualBook = bookConverter.dtoToString(bookDto);

        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("корректно преобразовывать модель в DTO. текущий метод modelToDto(Book book)")
    @Test
    void modelToDtoTest() {
        BookDto expectedBookDto = bookDto;
        when(authorConverter.modelToDto(author)).thenReturn(authorDto);
        for (int i = 0; i < genres.size(); i++) {
            when(genreConverter.modelToDto(genres.get(i)))
                    .thenReturn(genreDtos.get(i));
        }
        BookDto actualBookDto = bookConverter.modelToDto(book);

        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    private static Author getAuthor() {
        return new Author(FIRST_AUTHOR_ID, "Author_1");
    }

    private static List<Genre> getGenres() {
        return IntStream.range(1, 3).boxed().map(id -> new Genre("g" + id, "Genre_" + id)).toList();
    }

    private static Book getBook(Author author, List<Genre> dbGenres) {
        return new Book(FIRST_BOOK_ID, "BookTitle_1", author, dbGenres);
    }

    private static AuthorDto getAuthorDto() {
        return new AuthorDto(FIRST_AUTHOR_ID, "Author_1");
    }

    private static List<GenreDto> getGenreDtos() {
        return IntStream.range(1, 3).boxed().map(id -> new GenreDto("g" + id, "Genre_" + id)).toList();
    }

    private static BookDto getBookDto(AuthorDto author, List<GenreDto> dbGenreDtos) {
        return new BookDto(FIRST_BOOK_ID, "BookTitle_1", author, dbGenreDtos);
    }
}
