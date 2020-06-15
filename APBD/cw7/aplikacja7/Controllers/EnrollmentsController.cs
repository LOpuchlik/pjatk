using aplikacja7.DTOs.Requests;
using aplikacja7.Models;
using aplikacja7.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authorization.Infrastructure;
using Microsoft.AspNetCore.Mvc;


namespace aplikacja7.Controllers
{
    [ApiController]
   // [Authorize(Roles = "employee")]
   [Authorize(Roles= "student")]
    public class EnrollmentsController : ControllerBase
    {
        private readonly IStudentsDbService _studentsDbService;

        public EnrollmentsController(IStudentsDbService studentsDbService)
        {
            _studentsDbService = studentsDbService;
        }


        [HttpPost]
        [Route("api/enrollments")]
        // dodanie nowego studenta i zapisanie go na semestr
        public IActionResult EnrollStudent(EnrollStudentRequest request)
        {
            var st = new Student();

                st.FirstName = request.FirstName;
                st.LastName = request.LastName;
                st.BirthDate = request.BirthDate;
                st.Studies = request.Studies;
                st.IndexNumber = request.IndexNumber;

            Enrollment newStudentEnrollment = _studentsDbService.EnrollStudent(request);
            if (newStudentEnrollment == null)
            {
                return BadRequest(newStudentEnrollment);
            }

            return Ok(newStudentEnrollment);

        }  // end of method EnrollStudent



        [HttpPost]
        [Route("api/enrollments/promotions")]
        // promocja studentow na nowy semestr danych studiow
        public IActionResult PromoteStudent(PromoteStudentsRequest request)
        {
            Enrollment newPromotion = _studentsDbService.PromoteStudents(request);
            if (newPromotion == null)
            {
                return BadRequest(newPromotion);
            }

            return Ok(newPromotion);

        }  // end of method PromoteStudent
    }
}

