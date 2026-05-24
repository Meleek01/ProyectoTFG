const API = import.meta.env.VITE_API_URL || ''

export async function login(username, password) {
  const res = await fetch(`${API}/api/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })
  const data = await res.json()
  if (!res.ok || !data.success) throw new Error(data.message || 'Error al iniciar sesión')
  return data
}

export async function register(username, password) {
  const res = await fetch(`${API}/api/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })
  if (!res.ok) {
    const msg = await res.text()
    throw new Error(msg || 'Error al registrarse')
  }
  return res.text()
}

export function saveToken(token) {
  localStorage.setItem('fithero_token', token)
}

export function getToken() {
  return localStorage.getItem('fithero_token')
}

export function logout() {
  localStorage.removeItem('fithero_token')
}
