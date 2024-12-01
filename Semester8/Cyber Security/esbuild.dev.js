const esbuild = require("esbuild");
const postCssPlugin = require('esbuild-style-plugin')

const config = {
  minify: false,
  bundle: true,
  loader: { ".ts": "ts" },
}

async function watch() {
  let modal_context = await esbuild.context({
    entryPoints: ["./src/modal/app.tsx", './src/style/global.css'],
    outdir: './build',
    plugins: [
      postCssPlugin({
        postcss: {
          plugins: [require('tailwindcss'), require('autoprefixer')],
        },
      }),
    ],
    ...config,
  });
  let popup_context = await esbuild.context({
    entryPoints: ["./src/popup/app.tsx", './src/style/global.css'],
    outdir: './popup',
    plugins: [
      postCssPlugin({
        postcss: {
          plugins: [require('tailwindcss'), require('autoprefixer')],
        },
      }),
    ],
    ...config,
  });
  await modal_context.watch();
  console.log('\x1b[36mModal Compiled successfully!\x1b[0m');
  await popup_context.watch();
  console.log('\x1b[36mPopup Compiled successfully!\x1b[0m');
  console.log('\x1b[33mStarted development server... \x1b[0m');
}
watch();
