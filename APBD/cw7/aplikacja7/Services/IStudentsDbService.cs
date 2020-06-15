using System.Collections.Generic;
using System.Security.Claims;
using aplikacja7.DTOs.Requests;
using aplikacja7.Models;


namespace aplikacja7.Services
{
    public interface IStudentsDbService
    {
        // from StudentsController
        public List<Student> GetStudents();  // cala lista studentow
        public Student GetStudent(string IndexNumber); // pojedynczy student po indeksie
        public void UpdateStudent(int id);
        public void DeleteStudent(int id);
        public Claim[] Login(LoginRequestDto request);


        // from EnrollmentsController
        public Enrollment EnrollStudent(EnrollStudentRequest request);
        // from PromoteStudentsController
        public Enrollment PromoteStudents(PromoteStudentsRequest request);



    }
}
