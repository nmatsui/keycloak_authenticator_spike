import createHttpError from "http-errors";
import express, { Request, Response, NextFunction } from "express";
import session from "express-session";
import cookieParser from "cookie-parser";
import logger from "morgan";

import passport from "./libs/passport";
import { router as indexRouter } from "./routes/index";
import { router as authRouter } from "./routes/auth";
import { router as userInfoRouter } from "./routes/userinfo";
import { router as tokenInfoRouter } from "./routes/tokeninfo";

const app = express();

app.set("views", "./src/views");
app.set("view engine", "ejs");

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static("./public"));
app.use(session({
    secret: 'foobar',
    resave: false,
    saveUninitialized: false,
}));

app.use(passport.initialize());
app.use(passport.session());

app.use("/", indexRouter);
app.use("/", authRouter);
app.use("/", userInfoRouter);
app.use("/", tokenInfoRouter);

app.use((req: Request, res: Response, next: NextFunction) =>
    next(createHttpError(404))
);
app.use((err: any, req: Request, res: Response, next: NextFunction) => {
    res.locals.message = err.message;
    res.locals.error = req.app.get("env") === "development" ? err : {};

    res.status(err.status || 500);
    res.render("error");
});

app.listen(3000,()=>{
    console.log('start port to 3000')
});

module.exports = app;
