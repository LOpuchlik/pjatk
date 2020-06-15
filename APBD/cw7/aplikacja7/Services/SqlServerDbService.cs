
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using aplikacja7.DTOs.Requests;
using aplikacja7.Models;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc;

namespace aplikacja7.Services
{
    public class SqlServerDbService : IStudentsDbService
    {

        private const string CONNECTION_STRING = "Data Source=db-mssql;Initial Catalog=s16478;Integrated Security=True";
        // from EnrolmentsController
        int _idStudies;
        int _generalMaxIdEnrollment = 1;
        int _idEnrollment = 1;

        public void DeleteStudent(int id)
        {
            using (var con = new SqlConnection(CONNECTION_STRING))
            using (var com = new SqlCommand())
            {
                com.Connection = con;
                con.Open();
                var transaction = con.BeginTransaction();
                com.Transaction = transaction;

                try
                {
                    com.CommandText = "DELETE FROM Student WHERE IndexNumber = @id";
                    com.Parameters.AddWithValue("id", id);
                    com.ExecuteNonQuery();
                    transaction.Commit();
                }
                catch (SqlException exc)
                {
                    transaction.Rollback();
                }
            }
        }

        public Student GetStudent(string IndexNumber)
        {
            using (var client = new SqlConnection(CONNECTION_STRING))
            using (var command = new SqlCommand())
            {
                command.Connection = client;
                command.CommandText = "SELECT Student.IndexNumber, Student.FirstName, Student.LastName, Student.BirthDate, Enrollment.Semester, Studies.Name FROM Enrollment JOIN Student ON Enrollment.IdEnrollment = Student.IdEnrollment JOIN Studies ON Enrollment.IdStudy = Studies.IdStudy WHERE IndexNumber = @IndexNo";
                command.Parameters.AddWithValue("IndexNo", IndexNumber);


                client.Open();
                var dataReader = command.ExecuteReader();
                if (dataReader.Read())
                {
                    var st = new Student();
                    st.IndexNumber = dataReader["IndexNumber"].ToString();
                    st.FirstName = dataReader["FirstName"].ToString();
                    st.LastName = dataReader["LastName"].ToString();
                    st.BirthDate = DateTime.Parse(dataReader["BirthDate"].ToString());
                    st.Semester = (int)dataReader["Semester"];
                    st.Studies = dataReader["Name"].ToString();
                    return st;

                }

            }
            return null;
        }

        public List<Student> GetStudents()
        {
            var students = new List<Student>();

            // -------------------------------------   zadanie 4.1
            using (var client = new SqlConnection(CONNECTION_STRING))
            using (var command = new SqlCommand())
            {
                command.Connection = client;

                // --------------------------------    zadanie 4.2 // dla IndexNumber wyświetla się null, bo nie ma go w zapytaniu SQLowym, gdyż nie było go w poleceniu do zadania
                command.CommandText = "SELECT Student.IndexNumber, Student.FirstName, Student.LastName, Student.BirthDate, Enrollment.Semester, Studies.Name From Enrollment JOIN Student on Enrollment.IdEnrollment = Student.IdEnrollment JOIN Studies on Enrollment.IdStudy = Studies.IdStudy";


                client.Open();
                SqlDataReader dataReader = command.ExecuteReader();  // strumień typu forward only
                while (dataReader.Read())
                {
                    var st = new Student();
                    st.FirstName = dataReader["FirstName"].ToString();
                    st.LastName = dataReader["LastName"].ToString();
                    st.BirthDate = DateTime.Parse(dataReader["BirthDate"].ToString());
                    st.Semester = (int)dataReader["Semester"];
                    st.Studies = dataReader["Name"].ToString();
                    students.Add(st);  // mam liste studentów sparsowaną do formatu JSON

                }
            }
            return students;
        }

        public void UpdateStudent(int id)
        {
            throw new System.NotImplementedException();
        }

        public Enrollment EnrollStudent(EnrollStudentRequest request)
        {
            using (var con = new SqlConnection(CONNECTION_STRING))
            using (var com = new SqlCommand())
            {
                com.Connection = con;
                con.Open();
                var transaction = con.BeginTransaction();
                com.Transaction = transaction;

                try
                {
                    // sprawdzam czy podane studia istnieja w bazie?
                    com.CommandText = "SELECT IdStudy from Studies WHERE Name = @name";
                    com.Parameters.AddWithValue("name", request.Studies);

                    var dataReader = com.ExecuteReader();
                    if (!dataReader.Read())
                    {
                        transaction.Rollback();
                        return null;
                    }
                    // numer studies id
                    _idStudies = (int)dataReader["IdStudy"];

                    dataReader.Close();

                    // wyciągam ogólnie największy IdEnrollment, żeby póżniej dodać nowy Enrollment z tą wartoscia zwiekszona o 1
                    com.CommandText = "select max(ISNULL(IdEnrollment,0)) from Enrollment";
                    dataReader = com.ExecuteReader();
                    if (dataReader.Read())
                    {
                        _generalMaxIdEnrollment = (int)dataReader[0];
                    }

                    dataReader.Close();

                    // Sprawdzam czy jest enrollment dla danych studiow na semestr 1?
                    com.CommandText = "SELECT Max(IdEnrollment) from Enrollment WHERE Semester = 1 and IdStudy = (SELECT IdStudy from Studies WHERE Name = @studyName)";
                    com.Parameters.AddWithValue("studyName", request.Studies);
                    dataReader = com.ExecuteReader();

                    if (!dataReader.Read())
                    {
                        DateTime currentDate = DateTime.Now;
                        _idEnrollment = _generalMaxIdEnrollment + 1;
                        com.CommandText = "INSERT INTO Enrollment (IdEnrollment, Semester, IdStudy, StartDate) VALUES(@nextIdEnrollment, 1, @idStudies, @currentDate)";
                        com.Parameters.AddWithValue("nextIdEnrollment", _idEnrollment);
                        com.Parameters.AddWithValue("idStudies", _idStudies);
                        com.Parameters.AddWithValue("currentDate", currentDate);
                    }
                    _idEnrollment = (int)dataReader[0];
                    dataReader.Close();

                    // sprawdzenie czy istnieje w bazie ktos o podanym numerze indeksu
                    com.CommandText = "SELECT * FROM Student WHERE IndexNumber = @givenIndex";
                    com.Parameters.AddWithValue("givenIndex", request.IndexNumber);
                    dataReader = com.ExecuteReader();
                    if (dataReader.Read())
                    {
                        // jezeli istnieje wpis do bazy o podanym numerze indeksu
                        transaction.Rollback();
                        return null;
                    }
                    else
                    {
                        // jezeli nie istnieje wpis w tabeli Student z podanym numerem indeksu, to dodanie studenta
                        com.CommandText = "INSERT INTO Student(IndexNumber, FirstName, LastName, BirthDate, IdEnrollment) " +
                            "VALUES(@indexx, @firstname, @lastname, @birthdate, @enrollmentId)";
                        com.Parameters.AddWithValue("indexx", request.IndexNumber);
                        com.Parameters.AddWithValue("firstname", request.FirstName);
                        com.Parameters.AddWithValue("lastname", request.LastName);
                        com.Parameters.AddWithValue("birthdate", request.BirthDate);
                        com.Parameters.AddWithValue("enrollmentId", _idEnrollment);
                        dataReader.Close();

                        com.ExecuteNonQuery();
                    }
                    transaction.Commit();
                }
                catch (SqlException exc)
                {
                    transaction.Rollback();
                }
            }
            Enrollment newStudentEnrollment = new Enrollment
            {
                IdEnrollment = _idEnrollment,
                Semester = 1,
                IdStudy = _idStudies,
                StartDate = DateTime.Now
            };
            return newStudentEnrollment;
        }

        public Enrollment PromoteStudents(PromoteStudentsRequest request)
        {
            using (var con = new SqlConnection(CONNECTION_STRING))
            using (var com = new SqlCommand())
            {
                com.Connection = con;
                con.Open();
                var transaction = con.BeginTransaction();
                com.Transaction = transaction;

                com.CommandText = "execute spPromoteStudentToNextSemester @Name, @Semester;";
                com.Parameters.AddWithValue("Name", request.Name);
                com.Parameters.AddWithValue("Semester", request.Semester);


                try
                    {
                        com.ExecuteNonQuery();
                        transaction.Commit();
                } catch (SqlException e)
                    {
                        transaction.Rollback();
                        return null;
                    }


                com.CommandText = "SELECT * FROM Enrollment WHERE IdStudy = (SELECT IdStudy FROM Studies WHERE Name = @Name AND Semester = @Semester + 1)";
                Enrollment newPromotionEnrollment = null;
                var dataReader = com.ExecuteReader();
                {
                    if (dataReader.Read())
                    {
                        newPromotionEnrollment = new Enrollment()
                        {
                            IdEnrollment = (int)dataReader["IdEnrollment"],
                            IdStudy = (int)dataReader["IdStudy"],
                            Semester = (int)dataReader["Semester"],
                            StartDate = (DateTime)dataReader["StartDate"]
                        };
                    }
                    else
                    {
                        return null;
                    }
                    dataReader.Close();

                    return newPromotionEnrollment;
                }
            }
        }



        public Claim[] Login(LoginRequestDto request)
        {
            using (var connection = new SqlConnection(CONNECTION_STRING))
            using (var command = new SqlCommand())
            {
                command.Connection = connection;
                connection.Open();
                command.CommandText = "SELECT Role FROM Student WHERE IndexNumber = @index and Password = @password;";
                command.Parameters.AddWithValue("index", request.Login);
                command.Parameters.AddWithValue("password", request.Password);
                var dataReader = command.ExecuteReader();
                if (dataReader.Read())
                {
                    return new[]
                    {
                        new Claim(ClaimTypes.Name, request.Login),
                        new Claim(ClaimTypes.Role, dataReader["Role"].ToString())
                    };
                }
            }
            return null;
        }
    }
}
