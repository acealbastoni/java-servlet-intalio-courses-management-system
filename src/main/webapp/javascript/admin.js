          async function fetchModules() {
            try {
                const response = await fetch('FetchModulesServlet');
                if (!response.ok) {
                    throw new Error('Failed to fetch modules');
                }
                const data = await response.json();
                console.log('Fetched modules:', data);
                //alert(JSON.stringify(data, null, 2)); 

                const transformedData = transformData(data);
                console.log('Transformed Data:', transformedData); 
                //alert(JSON.stringify(transformedData, null, 2)); 

                displayCourses(transformedData); // Display courses and modules on the page
            } catch (error) {
                console.error('Error fetching modules:', error);
            }
        }

        function transformData(data) {
            const courseMap = {
                1: "Java",
                2: "JavaScript",
                3: "Python",
                4: "C#"
            };

            const moduleMap = {};

            data.forEach(item => {
                const courseName = courseMap[item.courseID];
                if (!moduleMap[courseName]) {
                    moduleMap[courseName] = {};
                }

                if (!moduleMap[courseName][item.moduleName]) {
                    moduleMap[courseName][item.moduleName] = [];
                }

                moduleMap[courseName][item.moduleName].push(item.pdfFileName);
            });

            const result = Object.keys(moduleMap).map(courseName => {
                return {
                    courseName,
                    modules: Object.keys(moduleMap[courseName]).map(moduleName => {
                        return {
                            moduleName,
                            pdfFiles: moduleMap[courseName][moduleName]
                        };
                    })
                };
            });

            return result;
        }

        function displayCourses(courses) {
            const coursesContainer = document.querySelector('.courses-container');
            coursesContainer.innerHTML = '<h2>Existing Courses and Modules</h2>'; // Clear existing content

            courses.forEach(course => {
                const courseDiv = document.createElement('div');
                courseDiv.classList.add('course');

                const courseTitle = document.createElement('h3');
                courseTitle.textContent = course.courseName;
                courseDiv.appendChild(courseTitle);

                const courseDescription = document.createElement('p');
                courseDescription.textContent = `Description of ${course.courseName}`;
                courseDiv.appendChild(courseDescription);

                const modulesDiv = document.createElement('div');
                modulesDiv.classList.add('modules');

                const modulesTitle = document.createElement('h4');
                modulesTitle.textContent = 'Modules';
                modulesDiv.appendChild(modulesTitle);

                course.modules.forEach(module => {
                    const moduleParagraph = document.createElement('p');
                    moduleParagraph.textContent = `${module.moduleName}: ${module.pdfFiles.join(', ')}`;
                    modulesDiv.appendChild(moduleParagraph);
                });

                courseDiv.appendChild(modulesDiv);
                coursesContainer.appendChild(courseDiv);
            });
        }

        // Fetch and display modules on page load
        fetchModules();
    