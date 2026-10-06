import { createRoot } from 'react-dom/client'
import './index.css'
import { Provider } from 'react-redux'
import {  RouterProvider } from 'react-router-dom'
import { router } from './app/router/Routes'
import { store } from './store/configureStore'

createRoot(document.getElementById('root')!).render(
 
  <Provider store={store}>
      <RouterProvider router={router} />
  </Provider>
)
