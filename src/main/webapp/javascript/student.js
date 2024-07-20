/*document.addEventListener("DOMContentLoaded", function() {
    const downloadLink = document.getElementById('downloadLink');
    downloadLink.addEventListener('click', function(event) {
        event.preventDefault(); // Prevent the default action of the link

        const link = document.createElement('a');
        link.href = 'DownloadModuleServlet'; // This will trigger the servlet to return the hardcoded file
        link.download = '16cbabe7-2851-49c1-9a46-1addfc1c5348.pdf'; // Set the download attribute with the file name
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    });
});
*/