package com.dyurekdeler.OnlineMovieStoreInventory.controller

import com.dyurekdeler.OnlineMovieStoreInventory.entity.Movie
import com.dyurekdeler.OnlineMovieStoreInventory.repository.MovieRepository
import com.dyurekdeler.OnlineMovieStoreInventory.request.MovieRequest
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieRepository: MovieRepository
) {
    @GetMapping()
    fun getAllMovies(): ResponseEntity<List<Movie>> {
        val movies = movieRepository.findAll()
        return ResponseEntity.ok(movies)
    }

    @GetMapping("/{id}")
    fun getMovie(@PathVariable("id") id: String): ResponseEntity<Movie> {
        val movie = movieRepository.findOneById(ObjectId(id))
        return ResponseEntity.ok(movie)
    }

    @PostMapping
    fun createMovie(@RequestBody request: MovieRequest): ResponseEntity<Movie> {
        val movie = movieRepository.save(Movie(
            title = request.title,
            duration = request.duration,
            about = request.about,
            quantity = request.quantity
        ))
        return ResponseEntity(movie, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateMovie(@RequestBody request: MovieRequest, @PathVariable("id") id: String): ResponseEntity<Movie> {
        val movie = movieRepository.findOneById(ObjectId(id))
        val updatedMovie = movieRepository.save(Movie(
            id = movie.id,
            title = request.title,
            duration = request.duration,
            about = request.about,
            quantity = request.quantity,
            createdDate = movie.createdDate,
            modifiedDate = LocalDateTime.now()
        ))
        return ResponseEntity.ok(updatedMovie)
    }

    @DeleteMapping("/{id}")
    fun deleteMovie(@PathVariable("id") id: String): ResponseEntity<Unit> {
        movieRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

}