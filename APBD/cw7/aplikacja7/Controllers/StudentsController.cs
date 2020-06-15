using System.Data.SqlClient;
using aplikacja7.Models;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using aplikacja7.DTOs.Requests;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using aplikacja7.Services;

namespace aplikacja7.Controllers
{

        [ApiController]
        [Route("api/students")]         
    public class StudentsController : ControllerBase
        {
            private readonly IStudentsDbService _studentsDbService;
           public IConfiguration Configuration { get; set; }

        public StudentsController(IStudentsDbService studentsDbService, IConfiguration configuration)
        {
            Configuration = configuration;
            _studentsDbService = studentsDbService;
            }

         private const string CONNECTION_STRING = "Data Source=db-mssql;Initial Catalog=s16478;Integrated Security=True";

        [HttpGet]
       
        public IActionResult GetStudents()
        {
            return Ok(_studentsDbService.GetStudents());
        }

        // ----------------------------------------   zadanie 4.5
        [HttpGet("{IndexNumber}")]
        public IActionResult GetStudent(string IndexNumber)
        {
            var student = _studentsDbService.GetStudent(IndexNumber);
            if (student == null)
            {
                return BadRequest("Nie znaleziono studenta"); ;
            }
            return Ok(student);
        }

     

        [HttpPut("{id}")]

        public IActionResult PutStudent(int id)
        {

            return Ok("Update successful");
        }


        [HttpDelete("{id}")]

        public IActionResult DeleteStudent(int id)
        {

            return Ok("Student deleted");
        }


        [HttpPost]
        [Route("login")]
        public IActionResult Login(LoginRequestDto request)
        {

            //var claims = _studentsDbService.Login(request);

            var claims = new[] {
            new Claim(ClaimTypes.Name, "Aneta"),
            new Claim(ClaimTypes.Role, "employee"),
            new Claim(ClaimTypes.Role, "student")
            };

            if (claims == null)
            {
                return Unauthorized();
            }

          
            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(Configuration["SecretKey"]));

            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
            var token = new JwtSecurityToken(
                issuer: "Gakko",
                audience: "Students",
                claims: claims,
                expires: DateTime.Now.AddMinutes(10),
                signingCredentials: creds
            );
            return Ok(new
            {
                token = new JwtSecurityTokenHandler().WriteToken(token),
                refreshToken = Guid.NewGuid()  // nowo generowany token
            });
        }

    }
}