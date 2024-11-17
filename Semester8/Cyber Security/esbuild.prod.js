const esbuild = require("esbuild");
const postCssPlugin = require("esbuild-style-plugin");

const config = {
  minify: false,
  bundle: true,
  loader: { ".ts": "ts" },
};

async function build() {
  await esbuild.build([
    {
      entryPoints: ["./src/modal/app.tsx"],
      outfile: "./build/bundle.js",
      ...config,
    },
  ]);
  await esbuild.build({
    entryPoints: ["./src/popup/app.tsx", "./src/popup/style.css"],
    outdir: "./popup",
    plugins: [
      postCssPlugin({
        postcss: {
          plugins: [require("tailwindcss"), require("autoprefixer")],
        },
      }),
    ],
    ...config,
  });
  console.log("\x1b[36mBuild successful!\x1b[0m");
}
build();
