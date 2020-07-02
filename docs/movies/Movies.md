## Movies
Movie's APIs requests and responses.

## Notes
If you are running this project with docker-compose the URL will start with `http://localhost:8080`

### List censored movies

**URL**: `/api/v1/movies?censura=CENSURADO`

**Method**: `GET`

#### Success Response

**Code**: `200 OK`

**Output Example**:

```json
[
    {
        "directed_by": [
            "James Wan"
        ],
        "cast": [
            "Leigh Whannell",
            "Cary Elwes",
            "Danny Glover",
            "Ken Leung",
            "Dina Meyer",
            "Mike Butters",
            "Paul Gutrecht",
            "Michael Emerson",
            "Benito Martinez",
            "Shawnee Smith"
        ],
        "updated_at": "2020-07-02T23:58:37Z[UTC]",
        "name": "Saw",
        "rating": "R",
        "created_at": "2020-07-02T23:58:37Z[UTC]",
        "launch_date": "2004-10-29",
        "id": "52bbc3a3-f640-4ade-831f-89ab661be668"
    },
    ...
]
```

#### Movie not found

**Code**: `404 Not Found`

**Output Example**:

```json
{
    "message": "Not found any movie."
}
```

### List not censored movies

**URL**: `/api/v1/movies?censura=SEM_CENSURA`

**Method**: `GET`

#### Success Response

**Code**: `200 OK`

**Output Example**:

```json
[
    {
        "directed_by": [
            "Jon Favreau"
        ],
        "cast": [
            "Donald Glover",
            "Seth Rogen",
            "Chiwetel Ejiofor",
            "Alfre Woodard",
            "Billy Eichner",
            "John Kani",
            "John Oliver",
            "Beyoncé Knowles-Carter"
        ],
        "updated_at": "2020-07-02T23:58:37Z[UTC]",
        "name": "Lion King",
        "rating": "G",
        "created_at": "2020-07-02T23:58:37Z[UTC]",
        "launch_date": "2019-07-09",
        "id": "3314fc8d-872b-4751-ae34-7e34bbc8022f"
    },
    ...
]
```

#### Movie not found

**Code**: `404 Not Found`

**Output Example**:

```json
{
    "message": "Not found any movie."
}
```

### Create movie

Create a new movie

**URL**: `/api/v1/movies`

**Method**: `POST`

**Body Example**:
```json
{
    "name": "Interstellar",
    "launch_date": "2014-10-26",
    "rating": "PG",
    "directed_by": [
        "Christopher Nolan"
    ],
    "cast": [
        "Matthew McConaughey",
        "Anne Hathaway",
        "Jessica Chastain",
        "Casey Affleck",
        "Wes Bentley"
    ]
}

```

#### Success Response

**Code**: `201 Created`

**Output Example**:

```json
{
  "message": "Movie created."
}
```

#### Movie already persisted

**Code**: `400 Bad request`

**Output Example**:

```json
{
    "message": "The movie Interstellar is already persisted on database."
}
```

#### Cast missing

**Code**: `400 Bad request`

**Output Example**:

```json
{
    "message": "The movie Interstellar dont have a cast."
}
```

#### Director missing

**Code**: `400 Bad request`

**Output Example**:

```json
{
    "message": "The movie Interstellar dont have any director."
}
```

#### Invalid body

**Code**: `400 Bad request`

**Output Example**:

```json
{
    "message": "Invalid body: this is an invalid body"
}
```

#### Invalid cast length

**Code**: `400 Bad request`

**Output Example**:

```json
{
    "message": "Invalid cast length (15). Max cast length: 10."
}
```