package play.modules.router;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.mvc.Router;
import play.utils.Java;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RouterAnnotationsPlugin extends PlayPlugin {

    @Override
    public void detectChange() {
        Router.load(Play.ctxPath);
        computeRoutes();
    }

    @Override
    public void onRoutesLoaded() {
        computeRoutes();
    }

    private boolean routeExist(String methodName, String action, String path) {
        boolean exists = false;
        for (Router.Route route : Router.routes) {
            boolean match = route.method.equals(methodName) && route.path.equals(path) && route.action.equals(action);
            if (match) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    protected void computeRoutes() {
        List<Class> controllerClasses = getControllerClasses();

        List<Method> annotatedMethods = Java.findAllAnnotatedMethods(controllerClasses, Gets.class);
        for (Method annotatedMethod : annotatedMethods) {
            Gets annotation = annotatedMethod.getAnnotation(Gets.class);
            if (annotation != null) {
                for (int i = annotation.value().length - 1; i >= 0; i--) {
                    Get get = annotation.value()[i];
                    if (get.priority() != -1 && !routeExist("GET", getControllerName(annotatedMethod) + "." + annotatedMethod.getName(), get.value())) {
                        Router.addRoute(get.priority(), "GET", get.value(), getControllerName(annotatedMethod) + "." + annotatedMethod.getName(), getFormat(get.format()), get.accept());
                    } else {
                        Router.prependRoute("GET", get.value(), getControllerName(annotatedMethod) + "." + annotatedMethod.getName(), getFormat(get.format()), get.accept());

                    }
                }
            }
        }
        List<Method> gets = Java.findAllAnnotatedMethods(controllerClasses, Get.class);
        for (Method get : gets) {
            Get annotation = get.getAnnotation(Get.class);
            if (annotation != null) {

                if (annotation.priority() != -1 && !routeExist("GET", getControllerName(get) + "." + get.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "GET", annotation.value(), getControllerName(get) + "." + get.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    Router.prependRoute("GET", annotation.value(), getControllerName(get) + "." + get.getName(), getFormat(annotation.format()), annotation.accept());
                }
            }
        }

        annotatedMethods = Java.findAllAnnotatedMethods(controllerClasses, Posts.class);
        for (Method annotatedMethod : annotatedMethods) {
            Posts annotation = annotatedMethod.getAnnotation(Posts.class);
            if (annotation != null) {
                for (int i = annotation.value().length - 1; i >= 0; i--) {
                    Post post = annotation.value()[i];
                    if (post.priority() != -1 && !routeExist("POST", getControllerName(annotatedMethod) + "." + annotatedMethod.getName(), post.value())) {
                        Router.addRoute(post.priority(), "POST", post.value(), getControllerName(annotatedMethod) + "." + annotatedMethod.getName(), getFormat(post.format()), post.accept());
                    } else {
                        Router.prependRoute("POST", post.value(), getControllerName(annotatedMethod) + "." + annotatedMethod.getName(), getFormat(post.format()), post.accept());

                    }
                }
            }
        }
        List<Method> posts = Java.findAllAnnotatedMethods(controllerClasses, Post.class);
        for (Method post : posts) {
            Post annotation = post.getAnnotation(Post.class);
            if (annotation != null) {
                if (annotation.priority() != -1 && !routeExist("POST", getControllerName(post) + "." + post.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "POST", annotation.value(), getControllerName(post) + "." + post.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    Router.prependRoute("POST", annotation.value(), getControllerName(post) + "." + post.getName(), getFormat(annotation.format()), annotation.accept());
                }

            }
        }
        List<Method> puts = Java.findAllAnnotatedMethods(controllerClasses, Put.class);
        for (Method put : puts) {
            Put annotation = put.getAnnotation(Put.class);
            if (annotation != null) {
                if (annotation.priority() != -1 && !routeExist("PUT", getControllerName(put) + "." + put.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "PUT", annotation.value(), getControllerName(put) + "." + put.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    Router.prependRoute("PUT", annotation.value(), getControllerName(put) + "." + put.getName(), getFormat(annotation.format()), annotation.accept());
                }
            }
        }
        List<Method> options = Java.findAllAnnotatedMethods(controllerClasses, Options.class);
        for (Method option : options) {
            Options annotation = option.getAnnotation(Options.class);
            if (annotation != null) {
                if (annotation.priority() != -1 && !routeExist("OPTIONS", getControllerName(option) + "." + option.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "OPTIONS", annotation.value(), getControllerName(option) + "." + option.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    Router.prependRoute("OPTIONS", annotation.value(), getControllerName(option) + "." + option.getName(), getFormat(annotation.format()), annotation.accept());
                }
            }
        }
        List<Method> deletes = Java.findAllAnnotatedMethods(controllerClasses, Delete.class);
        for (Method delete : deletes) {
            Delete annotation = delete.getAnnotation(Delete.class);
            if (annotation != null) {
                if (annotation.priority() != -1 && !routeExist("DELETE", getControllerName(delete) + "." + delete.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "DELETE", annotation.value(), getControllerName(delete) + "." + delete.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    Router.prependRoute("DELETE", annotation.value(), getControllerName(delete) + "." + delete.getName(), getFormat(annotation.format()), annotation.accept());
                }
            }
        }

        List<Method> heads = Java.findAllAnnotatedMethods(controllerClasses, Head.class);
        for (Method head : heads) {
            Head annotation = head.getAnnotation(Head.class);
            if (annotation != null) {
                if (annotation.priority() != -1 && !routeExist("HEAD", getControllerName(head) + "." + head.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "HEAD", annotation.value(), getControllerName(head) + "." + head.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    Router.prependRoute("HEAD", annotation.value(), getControllerName(head) + "." + head.getName(), getFormat(annotation.format()), annotation.accept());
                }
            }
        }

        List<Method> webSockets = Java.findAllAnnotatedMethods(controllerClasses, WS.class);
        for (Method ws : webSockets) {
            WS annotation = ws.getAnnotation(WS.class);
            if (annotation != null) {
                if (annotation.priority() != -1 && !routeExist("WS", getControllerName(ws) + "." + ws.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "WS", annotation.value(), getControllerName(ws) + "." + ws.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    Router.prependRoute("WS", annotation.value(), getControllerName(ws) + "." + ws.getName(), getFormat(annotation.format()), annotation.accept());
                }
            }
        }

        List<Method> list = Java.findAllAnnotatedMethods(controllerClasses, Any.class);
        for (Method any : list) {
            Any annotation = any.getAnnotation(Any.class);
            if (annotation != null) {
                if (annotation.priority() != -1 && !routeExist("*", getControllerName(any) + "." + any.getName(), annotation.value())) {
                    Router.addRoute(annotation.priority(), "*", annotation.value(), getControllerName(any) + "." + any.getName(), getFormat(annotation.format()), annotation.accept());
                } else {
                    // Always the last one
                    Router.prependRoute("*", annotation.value(), getControllerName(any) + "." + any.getName(), getFormat(annotation.format()), annotation.accept());
                }
            }
        }

        for (Class clazz : controllerClasses) {
            StaticRoutes annotation = (StaticRoutes) clazz.getAnnotation(StaticRoutes.class);
            if (annotation != null) {
                ServeStatic[] serveStatics = annotation.value();
                if (serveStatics != null) {
                    for (ServeStatic serveStatic : serveStatics) {
                        if (serveStatic.priority() != -1) {
                            Router.addRoute(serveStatic.priority(), "GET", serveStatic.value(), "staticDir:" + serveStatic.directory(), serveStatic.accept());
                        } else {
                            Router.prependRoute("GET", serveStatic.value(), "staticDir:" + serveStatic.directory(), serveStatic.accept());
                        }
                    }
                }
            }
        }

        for (Class clazz : controllerClasses) {
            ServeStatic annotation = (ServeStatic) clazz.getAnnotation(ServeStatic.class);
            if (annotation != null) {
                if (annotation.priority() != -1) {
                    Router.addRoute(annotation.priority(), "GET", annotation.value(), "staticDir:" + annotation.directory(), annotation.accept());
                } else {
                    Router.prependRoute("GET", annotation.value(), "staticDir:" + annotation.directory(), annotation.accept());
                }
            }
        }

    }

    public List<Class> getControllerClasses() {
        List<Class> returnValues = new ArrayList<Class>();
        List<ApplicationClasses.ApplicationClass> classes = Play.classes.all();
        for (ApplicationClasses.ApplicationClass clazz : classes) {
            if (clazz.name.startsWith("controllers.")) {
                if (clazz.javaClass != null && !clazz.javaClass.isInterface() && !clazz.javaClass.isAnnotation()) {
                    returnValues.add(clazz.javaClass);
                }
            }
        }
        return returnValues;
    }

    private String getFormat(String format) {
        if (format == null || format.length() < 1) {
            return null;
        }
        return "(format:'" + format + "')";
    }

    private String getControllerName(Method method) {
        return method.getDeclaringClass().getName().substring(12, method.getDeclaringClass().getName().length());
    }
}
