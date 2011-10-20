<%@ page session="true"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sras.client.utils.*"%>
<%@ page import="com.sras.datamodel.*"%>
<%@ page import="com.sras.database.*"%>
<%@ page import="com.sras.api.shorturl.*"%>
<%@ page import="com.sras.api.common.*"%>
<%@ page import="com.sras.api.common.impl.*"%>
<%@ page import="org.joda.time.DateTime"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title dir="ltr">Encrypt URL</title>
<style type="text/css">
.btnav {
	background-color: #84C1DF;
}

body {
	font-family: "Helvetica Neue", Arial, sans-serif;
	font-size: 13px;
	color: #555;
	position: relative
}

a {
	color: blue;
}

a:visited {
	color: maroon;
}

a:active {
	color: red;
}

a:hover {
	color: purple;
	background: #AFB
}

textarea#url {
	width: 600px;
	height: 120px;
	border: 3px solid #cccccc;
	padding: 5px;
	font-family: Tahoma, sans-serif;
	background-image: url(bg.gif);
	background-position: bottom right;
	background-repeat: no-repeat;
}

ul {
	border: 0;
	margin: auto;
	padding: 0;
	list-style-type: none;
	text-align: center;
}

#pagination-flickr li {
	border: 0;
	margin: 0;
	padding: 0;
	font-size: 11px;
	list-style: none;
	display: inline;
}

#pagination-flickr a {
	border: solid 1px #DDDDDD;
	margin-right: 2px;
}

#pagination-flickr .previous-off,#pagination-flickr .next-off {
	color: #666666;
	display: block;
	float: right;
	font-weight: bold;
	padding: 3px 4px;
}

#pagination-flickr .next a,#pagination-flickr .previous a {
	font-weight: bold;
	border: solid 1px #FFFFFF;
}

#pagination-flickr .active {
	color: #ff0084;
	font-weight: bold;
	display: block;
	float: right;
	padding: 4px 6px;
}

#pagination-flickr a:link,#pagination-flickr a:visited {
	color: #0063e3;
	display: block;
	float: right;
	padding: 3px 6px;
	text-decoration: none;
}

#pagination-flickr a:hover {
	border: solid 1px #666666;
}
</style>
<script type="text/javascript">
	function submitForm(action)
	{
		var form = document.forms[0];
		if(action.name == "Shorten")
		{
			form.action="/encrypturl/default/encrypt.action";
		}
		else if(action.name == "Group")
		{
			form.action="/encrypturl/default/encrypt.action";
		}
		else if(action.name == "Explore")
		{
			form.action="/encrypturl/default/encrypt.action";
		}
		form.submit();
	}
</script>
</head>
<body>
<center>
<fieldset style="width: 700px; border: 0px;"><label>Encrypt
URL</label>
<form action="/encrypturl/default/encrypt.action" method="POST"><input
	type="hidden" name="method" value="encrypt" /> <!--<input type="text"	name="url" id="url" value="" size="70" />-->
<textarea name="url" id="url" onfocus="this.value=''; setbg('#84C1DF');"
	onblur="setbg('white')">Enter your links here...</textarea> <br />
<input type="button" name="Shorten" value="Shorten"
	onclick="submitForm(this)" />&nbsp;&nbsp; <input type="button"
	name="Group" value="Group" onclick="submitForm(this)" />&nbsp;&nbsp; <input
	type="button" name="Explore" value="Explore" onclick="submitForm(this)" /></form>
</fieldset>

<br />

<!--
	<table style="width: 700px;">
		<tr>
			<td>
				<ul id="pagination-flickr">
					<li class="previous-off">«Previous</li>
					<li class="active">1</li>
					<li><a href="?page=2">2</a></li>
					<li><a href="?page=3">3</a></li>
					<li><a href="?page=4">4</a></li>
					<li><a href="?page=5">5</a></li>
					<li><a href="?page=6">6</a></li>
					<li><a href="?page=7">7</a></li>
					<li class="next"><a href="?page=8">Next »</a></li>
				</ul>
			</td>
		</tr>
	</table>
	-->
<table BORDER=0 CELLPADDING=3 CELLSPACING=1 RULES=ROWS FRAME=HSIDES
	style="width: 700px;">
	<tr style="background-color: #EDF4F6" height="30px">
		<th>Short Form</th>
		<th>Created</th>
		<th>Modified</th>
		<th>Original Form</th>
		<th>Privacy</th>
	</tr>

	<%
		UserData user = LoginUtils.getUser(request, response);
	    Collection<UrlData> urls = new ArrayList<UrlData>();
	    SUFactory suFactory = new SUFactory(user);
	    SUManager suManager = suFactory.getManager();
	    PageData pageData = new PageData();
	    urls = suManager.getShortUrls(pageData, false);
	    String domain = request.getScheme() + "://"
			    + request.getServerName() + request.getContextPath();
	    for (UrlData ud : urls) {
			String encodedUrl = ud.getShortUrl();
			String longUrl = ud.getLongUrl();
			String title = "";//HelperUtilities.getTitle(longUrl);
			//DateTime cdate = new DateTime(ud.getCreated());
			//DateTime udate = new DateTime(ud.getUpdated());
			Date cdate = new Date(ud.getCreated().getTime());
			Date udate = new Date(ud.getUpdated().getTime());
	%>
	<tr onmouseover="style.backgroundColor='#84C1DF';"
		onmouseout="style.backgroundColor='#FFFFFF'" height="30px">
		<td><a href="<%=request.getContextPath()%>/<%=encodedUrl%>"><%=domain%>/<%=encodedUrl%></a></td>
		<td><%=DateUtils.getFriendlyTime(cdate)%></td>
		<td><%=DateUtils.getFriendlyTime(udate)%></td>
		<td><a href="<%=longUrl%>"><%=longUrl%></a></td>
		<td><%=(!ud.isPrivacy()) ? "Public" : "Private"%></td>
		<td><%=title%></td>
	</tr>
	<%
	    }
	%>
</table>
</center>
</body>
