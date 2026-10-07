import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// The dev server runs on port 3000 to match the Backend's default
// `app.cors.allowed-origins` / `app.frontend.url` (http://localhost:3000).
export default defineConfig({
  plugins: [react()],
  server: { port: 3000, strictPort: true },
})
