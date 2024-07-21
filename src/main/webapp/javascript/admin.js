document.addEventListener("DOMContentLoaded", function() {
    fetchModules();
    populateCourseSelect();
});

async function fetchModules() {
    try {
        const response = await fetch('FetchModulesServlet');
        if (!response.ok) {
            throw new Error('Failed to fetch modules');
        }
        const data = await response.json();
        const transformedData = await transformData(data);
        displayCourses(transformedData);
    } catch (error) {
        console.error('Error fetching modules:', error);
    }
}

async function transformData(data) {
    const courseMap = {};
    try {
        const response = await fetch('getCourses');
        if (!response.ok) {
            throw new Error('Failed to fetch courses');
        }
        const courses = await response.json();
        courses.forEach(course => {
            courseMap[course.id] = course.name;
        });

        const moduleMap = {};
        data.forEach(item => {
            const courseName = courseMap[item.courseID];
            if (!moduleMap[courseName]) {
                moduleMap[courseName] = {};
            }
            if (!moduleMap[courseName][item.moduleName]) {
                moduleMap[courseName][item.moduleName] = [];
            }
            moduleMap[courseName][item.moduleName].push(item);
        });

        return Object.keys(moduleMap).map(courseName => ({
            courseName,
            modules: Object.keys(moduleMap[courseName]).map(moduleName => ({
                moduleName,
                pdfFiles: moduleMap[courseName][moduleName]
            }))
        }));
    } catch (error) {
        console.error('Error fetching courses:', error);
        return [];
    }
}

function displayCourses(courses) {
    const coursesContainer = document.querySelector('.courses-container');
    coursesContainer.innerHTML = '<h2>Existing Courses and Modules</h2>';

    courses.forEach(course => {
        const courseDiv = document.createElement('div');
        courseDiv.classList.add('course');

        const courseTitle = document.createElement('h3');
        courseTitle.textContent = course.courseName;
        courseDiv.appendChild(courseTitle);

        const modulesDiv = document.createElement('div');
        modulesDiv.classList.add('modules');

        const modulesTitle = document.createElement('h4');
        modulesTitle.textContent = 'Modules';
        modulesDiv.appendChild(modulesTitle);

        course.modules.forEach(module => {
            const moduleDiv = document.createElement('div');
            moduleDiv.classList.add('module');

            const moduleName = document.createElement('span');
            moduleName.textContent = module.moduleName;
            moduleDiv.appendChild(moduleName);

            module.pdfFiles.forEach(pdfFile => {
                const downloadBtn = document.createElement('button');
                downloadBtn.textContent = 'Download PDF';
                downloadBtn.classList.add('download-btn');
                downloadBtn.addEventListener('click', function() {
                    window.location.href = `DownloadPDF?fileGuid=${pdfFile.fileGuid}&moduleID=${pdfFile.moduleID}`;
                });

                moduleDiv.appendChild(downloadBtn);
            });

            modulesDiv.appendChild(moduleDiv);
        });

        courseDiv.appendChild(modulesDiv);
        coursesContainer.appendChild(courseDiv);
    });
}

async function populateCourseSelect() {
    try {
        const response = await fetch('getCourses');
        if (!response.ok) {
            throw new Error('Failed to fetch courses');
        }
        const courses = await response.json();
        const courseSelect = document.getElementById('courseSelect');
        courses.forEach(course => {
            const option = document.createElement('option');
            option.value = course.id;
            option.textContent = course.name;
            courseSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching courses:', error);
    }
}
