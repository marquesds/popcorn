# Popcorn

<img src="assets/img/popcorn.png" height="300" width="300" alt="Popcorn Logo">

Popcorn is an API to save and retrieve movies.

## Installation

If this is your first time running this project, you'll need to execute the following commands:
```shell
$ make start
$ make createdb
$ make filldb
```

If you already initiated the database, just execute:
```shell
$ make start
```

## Running tests

If this is your first time running the tests, you'll need to execute the following commands:
```shell
$ cp .env.template .env
$ make testdb
$ make testall
```

If you already initiated the test database, just execute:
```shell
$ make testall
```

## Documentation

You can import the Postman's collection with some requests [here](docs/postman/popcorn.postman_collection.json).

Links for entire documentation:

  - [Movies APIs](docs/movies/Movies.md)
  - [Libraries and Design decisions](docs/LibsAndDesignDecisions.md)

## Licence

MIT License

Copyright (c) 2020 marquesds

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
