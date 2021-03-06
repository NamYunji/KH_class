/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.44
 * Generated at: 2021-04-13 19:33:13 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.board;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import board.model.vo.Board;
import member.model.service.MemberService;
import member.model.vo.Member;

public final class boardUpdateForm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/views/common/header.jsp", Long.valueOf(1617742633228L));
    _jspx_dependants.put("/WEB-INF/views/common/footer.jsp", Long.valueOf(1616949802944L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("member.model.service.MemberService");
    _jspx_imports_classes.add("member.model.vo.Member");
    _jspx_imports_classes.add("board.model.vo.Board");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	// 사용자 알림메시지
	// return타입이 object이므로 다운캐스팅 필요
	String msg = (String)session.getAttribute("msg");
	// 한번 읽어내고, 메시지 변수에 담은 후에는 세션에서 바로 지워버리기 -> 일회용 msg만들기
	if(msg != null) session.removeAttribute("msg");
	String loc = (String)request.getAttribute("loc");	
	System.out.println("msg@header.jsp = " + msg);
	Member loginMember = (Member)session.getAttribute("loginMember");
	
	// 사용자 쿠키처리
	String saveId = null;
	// 헤더에서 이 처리 - 모든 jsp에서 header.jsp를 포함 - 모든 요청에서 이걸 검사하겠다는 것
	// 쿠키 가져오기 - getCookies() -> cookie 배열 리턴
	Cookie[] cookies = request.getCookies();
	// 쿠키 열람
	// 처음 접속했을 때는 cookie가 null이기 때문에, null이 아닐 때만 검사
	if(cookies != null){
		for(Cookie c : cookies){
			// key, value형식 (name, value)
			String name = c.getName();
			String value = c.getValue();
			System.out.println(name + " : " + value);
			
			// saveId라는 name값이 있다면 saveId에 value(사용자 아이디)를 저장
			if("saveId".equals(name))
				saveId = value;
		}
	}

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Hello MVC</title>\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.print(request.getContextPath());
      out.write("/css/style.css\" />\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/js/jquery-3.6.0.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("/* msg를 페이지 실행하자마자 보여줌 */\r\n");
      out.write("/* alert(< % = msg % >); */\r\n");
      out.write("/* alert(로그인에 성공했습니다.); -> alert(\"로그인에 성공했습니다.\");*/\r\n");
      out.write("/* null이 아닌 경우에만 alert하도록 */\r\n");
 if(msg != null) { 
      out.write("\r\n");
      out.write("\talert(\"");
      out.print( msg );
      out.write("\"); \r\n");
 } 
      out.write("\r\n");
      out.write("\r\n");
      out.write('\r');
      out.write('\n');
 if(loc != null) { 
      out.write("\r\n");
      out.write("\tlocation.href = \"");
      out.print( loc );
      out.write("\";\r\n");
 } 
      out.write("\r\n");
      out.write("/* 로그인 폼 유효성 검사 */\r\n");
      out.write("$(function(){\r\n");
      out.write("\t$(\"#loginFrm\").submit(function(){\r\n");
      out.write("\t\t/* 선택자가 아닌 태그객체 전달 */\r\n");
      out.write("\t\tvar $memberId = $(memberId);\r\n");
      out.write("\t\tvar $password = $(password);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(/^.{4,}/.test($memberId.val()) == false){\r\n");
      out.write("\t\t\talert(\"유효한 아이디를 입력하세요.\");\r\n");
      out.write("\t\t\t$memberId.select();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(/^.{4,}/.test($password.val()) == false){\r\n");
      out.write("\t\t\talert(\"유효한 비밀번호를 입력하세요.\");\r\n");
      out.write("\t\t\t$password.select();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"container\">\r\n");
      out.write("\t\t<header>\r\n");
      out.write("\t\t\t<h1>Hello MVC</h1>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"login-container\">\r\n");
      out.write("\t\t\t");
 if(loginMember == null) {
      out.write("\r\n");
      out.write("\t\t\t\t<!-- 로그인폼 시작 -->\r\n");
      out.write("\t\t\t\t<!-- 비밀번호가 URL에 드러나지 않도록, POST로 처리 -->\r\n");
      out.write("\t\t\t\t<form id=\"loginFrm\" action=\"");
      out.print( request.getContextPath() );
      out.write("/member/login\" method=\"POST\">\r\n");
      out.write("\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td><input type=\"text\" name=\"memberId\" id=\"memberId\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tplaceholder=\"아이디\" tabindex=\"1\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tvalue=\"");
      out.print( saveId != null ? saveId : "" );
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<td><input type=\"submit\" value=\"로그인\" tabindex=\"3\"></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td><input type=\"password\" name=\"password\" id=\"password\" placeholder=\"비밀번호\" tabindex=\"2\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td colspan=\"2\"><input type=\"checkbox\" name=\"saveId\" id=\"saveId\"\r\n");
      out.write("\t\t\t\t\t\t\t");
      out.print( saveId != null ? "checked" : "");
      out.write("/>\r\n");
      out.write("\t\t\t\t\t\t\t");
      out.write(" \r\n");
      out.write("\t\t\t\t\t\t\t<label for=\"saveId\">아이디저장</label>&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"button\" value=\"회원가입\" onclick=\"location.href='");
      out.print( request.getContextPath());
      out.write("/member/memberEnroll';\"></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t<!-- 로그인폼 끝-->\r\n");
      out.write("\t\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<table id=\"login\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print( loginMember.getMemberName() );
      out.write("님, 안녕하세요.</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"button\" value=\"내정보보기\" onclick=\"location.href='");
      out.print( request.getContextPath() );
      out.write("/member/memberView'\"/>\r\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<input type=\"button\" value=\"로그아웃\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"location.href='");
      out.print( request.getContextPath() );
      out.write("/member/logout';\"/>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!-- 메인메뉴 시작 -->\r\n");
      out.write("\t\t\t<nav>\r\n");
      out.write("\t\t\t\t<ul class=\"main-nav\">\r\n");
      out.write("\t\t\t\t\t<li class=\"home\"><a href=\"");
      out.print(request.getContextPath());
      out.write("\">Home</a></li>\r\n");
      out.write("\t\t\t\t\t<li class=\"notice\"><a href=\"#\">공지사항</a></li>\r\n");
      out.write("\t\t\t\t\t<li class=\"board\"><a href=\"");
      out.print(request.getContextPath());
      out.write("/board/boardList\">게시판</a></li>\r\n");
      out.write("\t\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t\t\t");
 if(loginMember != null && MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t<li class=\"members\"><a href=\"");
      out.print( request.getContextPath());
      out.write("/admin/memberList\">회원관리</a></li>\r\n");
      out.write("\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</nav>\r\n");
      out.write("\t\t\t<!-- 메인메뉴 끝-->\r\n");
      out.write("\t\t</header>\r\n");
      out.write("\t\t<section id=\"content\">");
      out.write("    \r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/css/board.css\" />\r\n");

	// board를 넘겨받아서 board객체에 담기
	Board board = (Board)request.getAttribute("board");

      out.write("\r\n");
      out.write("<section id=\"board-container\">\r\n");
      out.write("<h2>게시판 수정</h2>\r\n");
      out.write("<form\r\n");
      out.write("\tname=\"boardUpdateFrm\"\r\n");
      out.write("\taction=\"");
      out.print(request.getContextPath() );
      out.write("/board/boardUpdate\"\r\n");
      out.write("\tmethod=\"post\"\r\n");
      out.write("\tenctype=\"multipart/form-data\">\r\n");
      out.write("\t<!-- pk번호가 있어야 update문에서 몇번을 찾아서 수정하라는 식으로 전개됨 -->\r\n");
      out.write("\t<!-- 게시글 번호를 노출할 필요가 없으니 hidden으로 작성 -->\r\n");
      out.write("\t<input type=\"hidden\" name=\"no\" value=\"");
      out.print( board.getNo() );
      out.write("\" />\r\n");
      out.write("\t<table id=\"tbl-board-view\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<th>제 목</th>\r\n");
      out.write("\t\t<td><input type=\"text\" name=\"title\" value=\"");
      out.print( board.getTitle() );
      out.write("\" required></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<th>작성자</th>\r\n");
      out.write("\t\t<td>\r\n");
      out.write("\t\t\t<input type=\"text\" name=\"writer\" value=\"");
      out.print( board.getWriter() );
      out.write("\" readonly/>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<!-- input:file의 value속성은  보안성을 이유로 임의변경할 수 없다.\r\n");
      out.write("\t\t\t input:file태그가 정한 방법대로 파일을 선택해야만 한다. -->\r\n");
      out.write("\t\t<th>첨부파일</th>\r\n");
      out.write("\t\t<td >\r\n");
      out.write("\t\t\t<input type=\"file\" name=\"upFile\">\r\n");
      out.write("\t\t\t");
 if(board.getAttach() != null) {
      out.write("\r\n");
      out.write("\t\t\t<!-- 원래 파일명을 보여주는 차선책 -->\r\n");
      out.write("\t\t\t<p style=\"margin:5px 0;\">\r\n");
      out.write("\t\t\t\t<img src=\"");
      out.print( request.getContextPath() );
      out.write("/images/file.png\" width=\"16px\"/>\r\n");
      out.write("\t\t\t\t");
      out.print( board.getAttach().getOriginalFileName() );
      out.write("\r\n");
      out.write("\t\t\t\t<!-- 삭제 체크박스\r\n");
      out.write("\t\t\t\t\t체크했다면 value로 attachment의 고유번호 -->\r\n");
      out.write("\t\t\t\t<input type=\"checkbox\"\r\n");
      out.write("\t\t\t\tname=\"delFile\"\r\n");
      out.write("\t\t\t\tid=\"delFile\"\r\n");
      out.write("\t\t\t\tvalue=\"");
      out.print( board.getAttach().getNo());
      out.write("\"/>\r\n");
      out.write("\t\t\t\t<label for=\"delFile\">삭제</label>\r\n");
      out.write("\t\t\t</p>\r\n");
      out.write("\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<th>내 용</th>\r\n");
      out.write("\t\t<!-- textarea는 value속성이 아니라 시작태그와 종료태그 사이에 작성. -->\r\n");
      out.write("\t\t<td><textarea rows=\"5\" cols=\"50\" name=\"content\">");
      out.print( board.getContent() );
      out.write("</textarea></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<th colspan=\"2\">\r\n");
      out.write("\t\t\t<input type=\"submit\" value=\"수정하기\"/>\r\n");
      out.write("\t\t\t<input type=\"button\" value=\"취소\" onclick=\"history.go(-1);\"/>\r\n");
      out.write("\t\t</th>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("</section>\r\n");
      out.write("<script>\r\n");
      out.write("/* 파일 선택 변경시 나타나는 이벤트 */\r\n");
      out.write("$(\"[name=upFile]\").change(function(){\r\n");
      out.write("\tconsole.log($(this).val()); // 파일 선택 - 파일명, 선택 x - 빈문자열\r\n");
      out.write("\tif($(this).val() != \"\"){\r\n");
      out.write("\t\t// 파일 변경 -> 체크\r\n");
      out.write("\t\t$('#delFile')\r\n");
      out.write("\t\t\t.prop(\"checked\", true)\r\n");
      out.write("\t\t\t// 파일이 변경된다면, check박스를 누르지 못하도록\r\n");
      out.write("\t\t\t// 클릭했을 때 함수로 return false를 걸어주면 클릭이 작동하지 않게 비활성화\r\n");
      out.write("\t\t\t.on('click', function(){\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t} else {\r\n");
      out.write("\t\t// 파일을 변경하려다가 다시 파일 선택 취소 -> 체크 해제\r\n");
      out.write("\t\t$('#delFile').prop(\"checked\", false)\r\n");
      out.write("\t\t\t// 취소하면 다시 정상적으로 작동할 수 있도록 click했을 때의 이벤트핸들러 제거\r\n");
      out.write("\t\t\t// cf. on (이벤트 핸들러를 추가) <-> off (이벤트 핸들러 제거)\r\n");
      out.write("\t\t\t.off('click');\r\n");
      out.write("\t}\r\n");
      out.write("});\r\n");
      out.write("$(document.boardUpdateFrm).submit(function (){\r\n");
      out.write("\tvar $content = $(\"[name=content]\");\r\n");
      out.write("\tif(/^(.|\\n)+$/.test($content.val()) == false){\r\n");
      out.write("\t\talert(\"내용을 입력하세요\");\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\treturn true;\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\t\t</section>\r\n");
      out.write("\r\n");
      out.write("\t\t<footer>\r\n");
      out.write("\t\t\t<p>&lt;Copyright 1998-2021 <strong>KH정보교육원</strong>. All rights reserved.&gt;</p>\r\n");
      out.write("\t\t</footer>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
      out.write('\r');
      out.write('\n');
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
