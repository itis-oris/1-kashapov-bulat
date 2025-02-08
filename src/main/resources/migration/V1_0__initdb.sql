create table users (
                       id serial primary key,
                       username varchar(50) not null,
                       email varchar(100) not null,
                       password varchar(200) not null,
                       skillpoints int default 5 not null check (skillpoints >= 0),
                       avg_rating float default 0 check (avg_rating between 0 and 5),
                       avatar varchar(200) default 'default.png',
                       description varchar(400) default 'Hello, I`m new in knowHowNet!',
                       session_id varchar(400),
                       remember_id varchar(400)

);

create table users_skill (
                             id serial primary key,
                             name varchar(80) not null,
                             user_id int not null,
                             category varchar(80) not null,
                             description varchar(400) not null,
                             rating float check (rating >= 0 and rating <= 5) default 0,
                             foreign key (user_id) references users(id)

);
create table lesson (
                        id serial primary key,
                        teacher_id int not null,
                        student_id int not null,
                        teacher_skill_id int not null,
                        status varchar(10) default 'undone' not null,
                        foreign key (teacher_id) references users(id),
                        foreign key (student_id) references users(id),
                        foreign key (teacher_skill_id) references users_skill(id),
                        check (teacher_id <> student_id)
);


create table rate (
                      id serial primary key,
                      user_skill_id int,
                      user_id int,
                      rate int check (rate >= 0 and rate <= 5),
                      message varchar(400),
                      foreign key (user_skill_id) references users_skill(id),
                      foreign key (user_id) references users(id)
);

create table notification (
    id serial primary key,
    from_id int,
    to_id int not null,
    skill_id int,
    type varchar(20),
    message varchar(100),
    status varchar(20) default 'unchecked',
    date_time timestamp default current_timestamp,
    foreign key (skill_id) references users_skill(id)
)