package com.example.mainservice.service;


import com.example.mainservice.models.Book;
import com.example.mainservice.models.BookDTO;
import com.example.mainservice.models.BookKeepingDTO;
import com.example.mainservice.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final RestTemplate restTemplate;

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    public BookDTO convertToDTO(Book book){
        return modelMapper.map(book, BookDTO.class);
    }
    public Book convertToEntity(BookDTO bookDTO){
        return modelMapper.map(bookDTO, Book.class);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.findById(id).get();
    }

    public Book findByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).get();
    }

    public Book save(Book book){
        Book savedBook = bookRepository.save(book);
        Long request = savedBook.getId();
        String urlLibrary = "http://localhost:8081/library-service/api/keep/save";
        restTemplate.postForEntity(urlLibrary, request, String.class);
        return savedBook;
    }

    public List<BookDTO> findFree(){
        String urlLibrary = "http://localhost:8081/library-service/api/keep/free";
        ResponseEntity<BookKeepingDTO[]> response =  restTemplate.getForEntity(urlLibrary,BookKeepingDTO[].class);
        List<BookKeepingDTO> bookKeepingDTOS = Arrays.asList(response.getBody());
        List<Book> books = findAll();
        List<BookDTO> result = new ArrayList<>();
        for (Book book : books) {
            boolean isBookFree = true;

            for (BookKeepingDTO bookKeep : bookKeepingDTOS) {
                if (book.getId().equals(bookKeep.getId()) && bookKeep.getReturnAt().isAfter(LocalDateTime.now())) {
                    isBookFree = false;
                    break;
                }
            }
            if (isBookFree) {
                result.add(convertToDTO(book));
            }
        }
        return result;
    }

    @Transactional
    public void delete(Long id){
        bookRepository.deleteById(id);
    }


}
