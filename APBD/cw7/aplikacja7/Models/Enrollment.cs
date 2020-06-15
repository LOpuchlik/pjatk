using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace aplikacja7.Models
{
    public class Enrollment
    {
        public int IdEnrollment { get; set; }
        [Range(1,7)]
        public int Semester { get; set; }
        public int IdStudy { get; set; }
        public DateTime StartDate { get; set; }
    }
}
