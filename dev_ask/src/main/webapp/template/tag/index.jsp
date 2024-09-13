<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<c:import url="/template/common/header.jsp"></c:import>
</head>
  <body style="background: #171527;">
	<c:import url="/template/common/nav.jsp" ></c:import>
	    <section class="container my-5">
      <div class="row">
        <div class="col-2">
          <div
            class="d-flex align-items-center justify-content-between  w-100" style="border-bottom: 1px solid #201D31;"
          >
            <h6 class="text-light">300 Tags</h6>
            <button class="btn btn-sm mb-2 text-light" style="background-color: #201D31;border: 1px solid#2B293C;" style="font-size: 13px" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
              <i class="bi bi-plus-circle-fill"></i> New Tag
            </button>
          </div>
        </div>
        <div class="col-10 border-start">
          <div
            class="d-flex justify-content-between align-items-center mb-3" style="border-bottom: 1px solid #201D31;"
          >
            <h4 class="text-light">Tags</h4>
            <div class="d-flex">
              <div class="input-group input-group-sm mb-3 me-3 text-light">
                <input
                  type="text"
                  class="form-control text-light"
                  aria-label="Sizing example input"
                  aria-describedby="inputGroup-sizing-sm"
                  style="background-color: #201D31;border: 2px solid #2B293C;"
                  placeholder="Search..."
                />
                <span class="input-group-text text-light" id="inputGroup-sizing-sm"  style="background-color: #201D31;border: 3px solid #2B293C;"
                  ><i class="bi bi-search"></i
                ></span>
              </div>
              <div class="nav-item dropdown">
                <button
                  type="button"
                  class="btn btn-outline-success btn-sm dropdown-toggle"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  style="background-color: #FFC107;color: #171527;"
                >
                  <i class="bi bi-filter"></i> Filter
                </button>

                <ul class="dropdown-menu text-light" style="background-color: #FFC107;">
                  <li><a class="dropdown-item" style="color: #201D31;" href="#">Action</a></li>
                  <li><a class="dropdown-item" style="color: #201D31;" href="#">Another action</a></li>
                  <li>
                    <a class="dropdown-item" style="color: #201D31;" href="#">Something else here</a>
                  </li>
                  <li><hr class="dropdown-divider" /></li>
                  <li><a class="dropdown-item" style="color: #201D31;" href="#">Separated link</a></li>
                </ul>
              </div>
            </div>
          </div>

          <!-- Modal -->
          <div
            class="modal fade"
            id="staticBackdrop"
            data-bs-backdrop="static"
            data-bs-keyboard="false"
            tabindex="-1"
            aria-labelledby="staticBackdropLabel"
            aria-hidden="true"
          >
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h1 class="modal-title fs-5" id="staticBackdropLabel">
                    New tag
                  </h1>
                  <button
                    type="button"
                    class="btn-close"
                    data-bs-dismiss="modal"
                    aria-label="Close"
                  ></button>
                </div>
                <div class="modal-body">
                    <form action="">
                        <div class="mb-2">
                            <label for="" class="form-label">Tag Name</label><br>
                        <input type="text" class="form-control" placeholder="tagname">
                        </div>
                        <div class="mb-2">
                            <label for="" class="form-label">Description</label><br>
                            <textarea name="" id="" class="form-control" rows="5" placeholder="tag descritpion"></textarea>
                        </div>
                        <div class="text-end">
                            <button class="btn btn-danger btn-sm">Cancel</button>
                            <button class="btn btn-success btn-sm">Save</button>
                        </div>
                    </form>
                </div>
              </div>
            </div>
          </div>

          <div class="container my-2">
            <div class="row">
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">Python</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    Python is a versatile language known for its simplicity and
                    readability, often used in web development, data science,
                    and automation.
                  </p>
                </div>
              </div>
              <div class="col-3">
                <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">JavaScript</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    JavaScript is the backbone of web development, enabling
                    interactive web pages. It’s used in frontend, backend, and
                    full-stack development.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">Java</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    Java is a robust, object-oriented language widely used for
                    building enterprise-level applications, Android apps, and
                    web servers.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">C#</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    C# is a powerful language developed by Microsoft, commonly
                    used for developing Windows applications, web apps, and game
                    development with Unity.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">Ruby</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    Ruby is known for its elegant syntax and is primarily used
                    in web development with the Ruby on Rails framework, which
                    emphasizes convention over configuration.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">PHP</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    PHP is a server-side scripting language that powers many of
                    the world’s websites and content management systems,
                    including WordPress.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">Swift</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    Swift is Apple’s programming language for iOS and macOS
                    apps, known for its performance and safety features, making
                    it ideal for mobile app development.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">Go</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    Go, or Golang, is a statically typed language developed by
                    Google, valued for its simplicity, concurrency support, and
                    performance in cloud computing.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">Kotlin</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    Kotlin is a modern, statically typed language that
                    interoperates with Java and is officially supported for
                    Android app development by Google.
                  </p>
                </div>
              </div>
              <div class="col-3">
                 <div class="card p-2 mb-2 text-light" style="background-color: #2B293C;">
                  <h6><a href="#">Rust</a></h6>
                  <p class="p-2" style="font-size: 13px">
                    Rust is a systems programming language known for its safety
                    and performance, particularly in memory management, making
                    it popular for systems development.
                  </p>
                </div>
              </div>
            </div>
          </div>

          <nav aria-label="Page navigation ">
            <ul class="pagination pagination-sm" >
              <li class="page-item">
                <a class="page-link" href="#"  style="background-color: #2B293C;border: 1px solid #201D31;">Previous</a>
              </li>
              <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">1</a></li>
              <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">2</a></li>
              <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">3</a></li>
              <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">Next</a></li>
            </ul>
          </nav>
        </div>
      </div>
    </section>
	<c:import url="/template/common/footer.jsp"></c:import>
</body>
</html>