async function fetchModules() {
	try {
		const response = await fetch('FetchModulesServlet');
		if (!response.ok) {
			throw new Error('Failed to fetch modules');
		}
		const data = await response.json();
		console.log('Fetched modules:', data);

		// Transform data after fetching courses
		const transformedData = await transformData(data);
		console.log('Transformed Data:', transformedData);

		displayCourses(transformedData); // Display courses and modules on the page
	} catch (error) {
		console.error('Error fetching modules:', error);
	}
}

async function transformData(data) {
	// Initialize courseMap
	const courseMap = {};

	try {
		// Fetch courses
		const response = await fetch('getCourses');
		if (!response.ok) {
			throw new Error('Failed to fetch courses');
		}
		const courses = await response.json();

		// Populate courseMap
		courses.forEach(course => {
			courseMap[course.id] = course.name;
		});

		console.log(courseMap);

		// Continue with transforming data
		const moduleMap = {};

		data.forEach(item => {
			const courseName = courseMap[item.courseID];
			if (!moduleMap[courseName]) {
				moduleMap[courseName] = {};
			}

			if (!moduleMap[courseName][item.moduleName]) {
				moduleMap[courseName][item.moduleName] = [];
			}

			moduleMap[courseName][item.moduleName].push({
				pdfFileName: item.pdfFileName,
				moduleID: item.moduleID
			});
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

	} catch (error) {
		console.error('Error fetching courses:', error);
		return [];
	}
}


//█████████████████████ █████████████████████████████ █████████████████████████████ 
// Call fetchModules to start the process
fetchModules();

//█████████████████████ █████████████████████████████ █████████████████████████████ 
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
			moduleParagraph.textContent = `${module.moduleName}: `;
			module.pdfFiles.forEach((pdfFile, index) => {
				const pdfLink = document.createElement('a');
				pdfLink.href = `uploads/${pdfFile.pdfFileName}`;
				pdfLink.textContent = pdfFile.pdfFileName;
				pdfLink.addEventListener('click', function(event) {
					event.preventDefault(); // Prevent the default action of the link
					alert(`Module ID: ${pdfFile.moduleID}`); // Alert the module ID
					
					const link = document.createElement('a');
					link.href = `DownloadModuleServlet?moduleID=${pdfFile.moduleID}`;
					link.download = pdfFile.pdfFileName; // Set the download attribute with the file name
					document.body.appendChild(link);
					link.click();
					document.body.removeChild(link);
					
					
				});
				moduleParagraph.appendChild(pdfLink);

				if (index < module.pdfFiles.length - 1) {
					moduleParagraph.appendChild(document.createTextNode(', '));
				}
			});
			modulesDiv.appendChild(moduleParagraph);
		});

		courseDiv.appendChild(modulesDiv);
		coursesContainer.appendChild(courseDiv);
	});
}


//█████████████████████ █████████████████████████████ █████████████████████████████ 
// Fetch and display modules on page load
fetchModules();

//████████████████████████████████████████ Add Module Form █████████████████████████████████████████████████████	
document.addEventListener("DOMContentLoaded", function() {
	fetch('getCourses')
		.then(response => response.json())
		.then(courses => {
			//alert(courses);
			const courseSelect = document.getElementById('courseSelect');
			courses.forEach(course => {
				const option = document.createElement('option');
				option.value = course.id;
				option.textContent = course.name;
				courseSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error fetching courses:', error));
});








