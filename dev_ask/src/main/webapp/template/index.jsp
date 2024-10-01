<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<c:import url="common/header.jsp"></c:import>
</head>
 <style>

.hero-img {
  /* position: absolute; */
  animation: moveImage 2.5s linear infinite; /* Adjust the duration and easing as needed */
}

@keyframes moveImage {
  0% {
    transform: translateX();
  }
  50% {
    transform: translateX(20px); /* Adjust the distance as needed */
    transform: translateY(10px); /* Adjust the distance as needed */
  }
  100% {
    transform: translateX(0);
  }
}
    @media screen and (max-width: 1200px) {
      .hero-img{
        opacity: .25;
      }
    }
    @media screen and (max-width: 900px) {
      .hero-img{
        opacity: .25;
      }
    .hero-img:nth-child(2),
    .hero-img:nth-child(4){
      opacity: 0;
    }
    }
    
  </style>
  <body style="background: #171527;">
    <!-- Header -->
    <c:import url="common/nav.jsp"></c:import>

    <!-- Hero Section -->
     <div class="container text-light text-center my-4 position-relative w-100">
      <img src="../image/laptop_with_web_layout.png" alt="" class="hero-img position-absolute sm:opacity-25 md:opacity-75 sm:w-25" style="top: 10%;left: 0%;" width="120px">
      <img src="../image/star.png" alt="" class="hero-img position-absolute sm:opacity-25 md:opacity-75 " style="top: 60%;left: 15%;">
      <img src="../image/6306479.png" alt="" class="hero-img position-absolute sm:opacity-25 md:opacity-75 " style="top: 10%;right: 5%;" width="150px">
      <img src="../image/web_browser.png" alt="" class="hero-img position-absolute sm:opacity-25 md:opacity-75 " style="top: 60%;right: 15%;" width="120px">
        <h1 class="mt-5 pt-5"><span style="color:#FFC107">Dev</span> Ask</h1> 
        <h3 class="my-4 text-secondary" style="line-height: 50px;">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. <br>
         amet consectetur adipisicing elit.
        </h3>
        <div class="my-5">
            <img src="../image/11101993.png" alt="" width="350px">
        </div>
        <button class="btn rounded-pill px-4 shadow" style="background-color: #FFC107;color: #171527;font-size: 20px;font-weight: bold;">Start <i class="bi bi-caret-right-fill pb-2"></i> </button>
     </div>

    <!-- Website Info Section -->
    <section class="container my-5">
        <div class="row py-5">
          <div class=" col-md-4">
            <div class="text-light text-center border-0">
              <img src="../image/test.png" width="75px" alt="">
              <h5 class="my-3">Explore Topics</h5>
              <div class="card-body text-secondary">
                Discover a variety of topics and tags that interest you. Browse
                through the most popular questions and find answers that help you
                grow.
                
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="text-light text-center border-0">
                <img src="../image/community.png" width="75px" alt="">
              <h5 class="my-3">Join the Community</h5>
              <div class="card-body text-secondary">
                Connect with fellow developers and experts. Share your knowledge,
                learn from others, and build your reputation in the community.
                
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="text-light text-center border-0">
                <img src="../image/star.png" width="75px" alt="">
              <h5 class="my-3">Get Recognized</h5>
              <div class="card-body text-secondary">
                Earn reputation points and badges for your contributions. Answer
                questions, share your expertise, and get recognized for your
                efforts.
              </div>
            </div>
          </div>
        </div>
      </section>

      <section class="container text-light my-5">
        <div class="row">
            <div class="col-12 col-md-6 col-lg-4">
                <div class="d-flex">
                    <div class="me-3"><div class="h3 rounded text-center" style="color: #FFC107;background-color: #2B293C;padding: 8px 12px;"><i class="bi bi-tags"  ></i></div></div>
                    <div>
                        <h5>Programming Languages</h5>
                        <p class="text-secondary">Explore questions and answers on popular programming languages like JavaScript, Python, Java, and more.</p>
                    </div>
                </div>
            </div>
             <div class="col-12 col-md-6 col-lg-4">
                <div class="d-flex">
                    <div class="me-3"><div class="h3 rounded text-center" style="color: #FFC107;background-color: #2B293C;padding: 8px 12px;"><i class="bi bi-tags"  ></i></div></div>
                    <div>
                        <h5>Web Development</h5>
                        <p class="text-secondary">Find resources and discussions on front-end and back-end development, frameworks, and best practices.</p>
                    </div>
                </div>
            </div>
             <div class="col-12 col-md-6 col-lg-4">
                <div class="d-flex">
                    <div class="me-3"><div class="h3 rounded text-center" style="color: #FFC107;background-color: #2B293C;padding: 8px 12px;"><i class="bi bi-tags"  ></i></div></div>
                    <div>
                        <h5>Database Management</h5>
                        <p class="text-secondary">Delve into questions about SQL, NoSQL, database design, and optimization techniques.</p>
                    </div>
                </div>
            </div>
             <div class="col-12 col-md-6 col-lg-4">
                <div class="d-flex">
                    <div class="me-3"><div class="h3 rounded text-center" style="color: #FFC107;background-color: #2B293C;padding: 8px 12px;"><i class="bi bi-tags"  ></i></div></div>
                    <div>
                        <h5>Tags</h5>
                        <p class="text-secondary">Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eaque, quae a fugiat quisquam debitis </p>
                    </div>
                </div>
            </div>
             <div class="col-12 col-md-6 col-lg-4">
                <div class="d-flex">
                    <div class="me-3"><div class="h3 rounded text-center" style="color: #FFC107;background-color: #2B293C;padding: 8px 12px;"><i class="bi bi-tags"  ></i></div></div>
                    <div>
                        <h5>Tags</h5>
                        <p class="text-secondary">Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eaque, quae a fugiat quisquam debitis </p>
                    </div>
                </div>
            </div>
             <div class="col-12 col-md-6 col-lg-4">
                <div class="d-flex">
                    <div class="me-3"><div class="h3 rounded text-center" style="color: #FFC107;background-color: #2B293C;padding: 8px 12px;"><i class="bi bi-tags"  ></i></div></div>
                    <div>
                        <h5>Tags</h5>
                        <p class="text-secondary">Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eaque, quae a fugiat quisquam debitis </p>
                    </div>
                </div>
            </div>
        </div>
      </section>

      <!-- latest questions -->
    <section class="container my-5 " >
        <h3 class="text-center mb-4 text-light">Latest Questions</h3>
        <div class="list-group" style="background-color: #171527 !important;">
          <a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            How can I optimize a SQL query with multiple joins?
            <span class="badge bg-primary">SQL</span>
            <span class="badge bg-primary">Database</span>
            <span class="badge bg-secondary">3 Answers</span>
            <span class="text-secondary float-end">30 minutes ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            What are the best practices for securing a REST API?
            <span class="badge bg-primary">API</span>
            <span class="badge bg-primary">Security</span>
            <span class="badge bg-primary">REST</span>
            <span class="badge bg-secondary">5 Answers</span>
            <span class="text-secondary float-end">1 hour ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            How to implement a responsive navbar with Bootstrap 5?
            <span class="badge bg-primary">HTML</span>
            <span class="badge bg-primary">CSS</span>
            <span class="badge bg-primary">Bootstrap</span>
            <span class="badge bg-secondary">2 Answers</span>
            <span class="text-secondary float-end">2 hours ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            What is the difference between abstract class and interface in Java?
            <span class="badge bg-primary">Java</span>
            <span class="badge bg-primary">OOP</span>
            <span class="badge bg-secondary">4 Answers</span>
            <span class="text-secondary float-end">3 hours ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            How do I handle exceptions in a Python web application?
            <span class="badge bg-primary">Python</span>
            <span class="badge bg-primary">Web</span>
            <span class="badge bg-primary">Exception Handling</span>
            <span class="badge bg-secondary">3 Answers</span>
            <span class="text-secondary float-end">4 hours ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            How can I reduce the load time of my React application?
            <span class="badge bg-primary">React</span>
            <span class="badge bg-primary">Performance</span>
            <span class="badge bg-primary">JavaScript</span>
            <span class="badge bg-secondary">6 Answers</span>
            <span class="text-secondary float-end">5 hours ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            What are the key differences between Docker and Kubernetes?
            <span class="badge bg-primary">Docker</span>
            <span class="badge bg-primary">Kubernetes</span>
            <span class="badge bg-primary">DevOps</span>
            <span class="badge bg-secondary">5 Answers</span>
            <span class="text-secondary float-end">6 hours ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            How to create a multi-step form with validation in Angular?
            <span class="badge bg-primary">Angular</span>
            <span class="badge bg-primary">Forms</span>
            <span class="badge bg-primary">Validation</span>
            <span class="badge bg-secondary">2 Answers</span>
            <span class="text-secondary float-end">7 hours ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            What is the best way to manage state in a large Vue.js application?
            <span class="badge bg-primary">Vue.js</span>
            <span class="badge bg-primary">State Management</span>
            <span class="badge bg-primary">JavaScript</span>
            <span class="badge bg-secondary">4 Answers</span>
            <span class="text-secondary float-end">8 hours ago</span>
          </a>
<a href="#" class="list-group-item list-group-item-action text-light" style="background-color: #201D31 !important;border: 1px solid #2B293C;">
            How can I improve the SEO of a single-page application?
            <span class="badge bg-primary">SEO</span>
            <span class="badge bg-primary">Single-Page Application</span>
            <span class="badge bg-primary">JavaScript</span>
            <span class="badge bg-secondary">3 Answers</span>
            <span class="text-secondary float-end">9 hours ago</span>
          </a>
        </div>
        <div class="text-center mt-4">
          <a href="#" class="btn text-light" style="background-color: #201D31;border: 1px solid#2B293C;">View All Questions</a>
        </div>
      </section> 

    <!-- top contributors -->
    <section class="container my-5">
        <h3 class="text-center mb-4 text-light">Top Contributors</h3>
        <div class="row text-center gap-4 mx-1 justify-content-between">
          <div class="col-12 col-md-3 card text-center p-2 text-light" style="background-color: #201D31;border: 1px solid#2B293C;">
            <img
              src="https://randomuser.me/api/portraits/women/85.jpg"
              class="rounded-circle m-auto"
              alt="User"
              width="80"
              height="80"
            />
            <h5 class="my-3" style="color: #FFC107;">Jane Doe</h5>
            <p>Reputation: 1500</p>
          </div>
          <div class="col-12 col-md-3 card text-center p-2 text-light" style="background-color: #201D31;border: 1px solid#2B293C;">
            <img
              src="https://randomuser.me/api/portraits/men/7.jpg"
              class="rounded-circle m-auto"
              alt="User"
              width="80"
              height="80"
            />
            <h5 class="my-3" style="color: #FFC107;">Max</h5>
            <p>Reputation: 1500</p>
          </div>
          <div class="col-12 col-md-3 card text-center p-2 text-light" style="background-color: #201D31;border: 1px solid#2B293C;">
            <img
              src="https://randomuser.me/api/portraits/men/29.jpg"
              class="rounded-circle m-auto"
              alt="User"
              width="80"
              height="80"
            />
            <h5 class="my-3" style="color: #FFC107;">Jane Doe</h5>
            <p>Michel</p>
          </div>
          <!-- More contributors -->
        </div>
        <div class="text-center mt-4">
          <a href="#" class="btn text-light" style="background-color: #201D31;border: 1px solid#2B293C;">View All Users</a>
        </div>
      </section>

    <!-- popular tag  -->

    <!-- Footer -->
    <footer class="text-light py-4" style="background-color: #2B293C;">
      <div class="container">
        <div class="row">
          <div class="col-md-4">
            <h5>About DevAsk</h5>
            <p>
              DevAsk is a platform for developers to ask questions and share
              knowledge. Whether you're a beginner or an expert, you'll find the
              help you need here.
            </p>
          </div>
          <div class="col-md-4">
            <h5>Quick Links</h5>
            <ul class="list-unstyled">
              <li><a href="#" class="text-light">Home</a></li>
              <li><a href="#" class="text-light">Tags</a></li>
              <li><a href="#" class="text-light">Users</a></li>
              <li><a href="#" class="text-light">Questions</a></li>
              <li><a href="#" class="text-light">Contact Us</a></li>
            </ul>
          </div>
          <div class="col-md-4">
            <h5>Follow Us</h5>
            <ul class="list-unstyled d-flex">
              <li class="me-3">
                <a href="#" class="text-light"
                  ><i class="bi bi-facebook"></i
                ></a>
              </li>
              <li class="me-3">
                <a href="#" class="text-light"><i class="bi bi-twitter"></i></a>
              </li>
              <li class="me-3">
                <a href="#" class="text-light"
                  ><i class="bi bi-linkedin"></i
                ></a>
              </li>
              <li>
                <a href="#" class="text-light"><i class="bi bi-github"></i></a>
              </li>
            </ul>
          </div>
        </div>
        <div class="text-center mt-3">
          &copy; 2024 DevAsk. All rights reserved.
        </div>
      </div>
    </footer>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
  </body>
</html>