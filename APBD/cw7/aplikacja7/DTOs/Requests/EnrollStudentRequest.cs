using System;
using System.ComponentModel.DataAnnotations;


namespace aplikacja7.DTOs.Requests
{
    public class EnrollStudentRequest
    {
        [Required(ErrorMessage = "IndexNumber cannot be null and requires format ^s[0 - 9]+$")]
        [RegularExpression("^s[0-9]+$")]
        public string IndexNumber { get; set; }
        [Required(ErrorMessage ="Name cannot be null")]
        [MaxLength(100)]
        public string FirstName { get; set; }
        [Required(ErrorMessage = "LastName cannot be null")]
        public string LastName { get; set; }
        [Required(ErrorMessage = "Birthday cannot be null")]
        public DateTime BirthDate { get; set; }
        [Required(ErrorMessage = "Studies cannot be null")]
        public string Studies { get; set; }
    }
}
