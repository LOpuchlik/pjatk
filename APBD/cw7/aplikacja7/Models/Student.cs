using System;

namespace aplikacja7.Models
{
    public class Student
    {
        public string IndexNumber { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime BirthDate { get; set; }
        public string Password { get; set; }

        public int Semester { get; set; }
        public string Studies { get; set; }
        
    }
}
