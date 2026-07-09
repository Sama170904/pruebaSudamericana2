erDiagram
    USUARIO ||--o{ COMENTARIO : escribe
    USUARIO ||--o{ VOTO : realiza
    USUARIO ||--o{ TOKEN : posee
    TAREA ||--o{ COMENTARIO : recibe
    TAREA ||--o{ VOTO : recibe

    USUARIO {
        bigint user_id PK
        string nombre
        string apellido
        string rol
        string email
        string password
    }

    TAREA {
        bigint tarea_id PK
        string titulo
        string descripcion
        string estado
        string categoria
        datetime fecha_creacion
    }

    COMENTARIO {
        bigint comentario_id PK
        string comentario
        bigint tarea_id FK
        bigint usuario_id FK
    }

    VOTO {
        bigint voto_id PK
        bigint tarea_id FK
        bigint usuario_id FK
        string estado
        datetime fecha_creacion
    }

    TOKEN {
        int id PK
        string token
        string tokenType
        boolean revoked
        boolean expired
        bigint admin_id FK
    }