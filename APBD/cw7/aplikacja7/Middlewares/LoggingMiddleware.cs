using System;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;

namespace aplikacja7.Middlewares
{
    public class LoggingMiddleware
    {
        private readonly RequestDelegate _next;
        private static readonly string Path = "requestsLog.txt";

        public LoggingMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            context.Request.EnableBuffering();
            if (context.Request != null)
            {
                string path = context.Request.Path; // "api/students"
                string method = context.Request.Method; // GET, POST itp.
                string queryString = context.Request.QueryString.ToString();
                string bodyStr = "";

                using (var reader = new StreamReader(context.Request.Body, Encoding.UTF8, true, 1024, true))
                {
                    bodyStr = await reader.ReadToEndAsync();
                    context.Request.Body.Position = 0;
                }

                using (var fS = new FileStream(Path, FileMode.Append)) 
                {
                    string timestamp = DateTime.Now.ToString("dd/MM/yyyy @ HH:mm:ss");
                    var message =    timestamp + "\n" +
                                     method + " " + path + "\n" +
                                     queryString + "\n" +
                                     bodyStr +"\n" + 
                                     "------------------------------------------------------------------------------";
                    var dataToAppend = new UTF8Encoding().GetBytes(message);
                    fS.Write(dataToAppend);
                }
            }
            if (_next != null)
            {
                await _next(context);
            }
        }
    }
}
