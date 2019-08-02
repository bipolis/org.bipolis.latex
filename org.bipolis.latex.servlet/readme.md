# org.bipolis.latex.servlet

The latex project

## Source Repository

**Browse**: [https://github.com/bipolis/org.bipolis.latex/org.bipolis.latex.servlet](https://github.com/bipolis/org.bipolis.latex/org.bipolis.latex.servlet).

**Clone**: [scm:git:https://github.com/bipolis/org.bipolis.git/org.bipolis.latex.servlet](scm:git:https://github.com/bipolis/org.bipolis.git/org.bipolis.latex.servlet).

## Coordinates

### Maven

```xml
<dependency>
    <groupId>org.bipolis</groupId>
    <artifactId>org.bipolis.latex.servlet</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### OSGi

```
Bundle Symbolic Name: org.bipolis.latex.servlet
Version             : 0.0.1.201908022331
```

## Components

### org.bipolis.latex.servlet.LatexServlet - *state = enabled, activation = delayed*

#### Services - *scope = singleton*

|Interface name |
|--- |
|javax.servlet.Servlet |

#### Properties

|Name |Type |Value |
|--- |--- |--- |
|osgi.http.whiteboard.servlet.pattern |String[] |["/latex"] |
|osgi.http.whiteboard.servlet.multipart.maxFileSize |Long |500000 |
|osgi.http.whiteboard.servlet.multipart.location |String |"" |
|osgi.http.whiteboard.servlet.multipart.enabled |Boolean |true |
|osgi.http.whiteboard.servlet.multipart.fileSizeThreshold |Integer |0 |
|osgi.http.whiteboard.servlet.multipart.maxRequestSize |Long |-1 |

#### Configuration - *policy = optional*

##### Pid: `org.bipolis.latex.servlet.LatexServlet`

No information available.

---
bipolis - [https://bipolis.org/](https://bipolis.org/)