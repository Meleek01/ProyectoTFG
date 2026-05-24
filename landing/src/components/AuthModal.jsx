import { useState } from 'react'
import { login, register, saveToken } from '../services/auth'

function LoginForm({ onSuccess }) {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const data = await login(username, password)
      saveToken(data.token)
      onSuccess(username)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <form className="modal-form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label>Usuario</label>
        <input
          type="text"
          placeholder="Tu nombre de héroe"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          autoFocus
        />
      </div>
      <div className="form-group">
        <label>Contraseña</label>
        <input
          type="password"
          placeholder="••••••••"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
      </div>
      {error && <p className="form-error">{error}</p>}
      <button className="btn btn-primary btn-block" type="submit" disabled={loading}>
        {loading ? 'Entrando...' : 'Iniciar sesión'}
      </button>
    </form>
  )
}

function RegisterForm({ onSuccess }) {
  const [form, setForm] = useState({ username: '', email: '', name: '', password: '', confirm: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  function set(field) {
    return (e) => setForm((prev) => ({ ...prev, [field]: e.target.value }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    if (form.password !== form.confirm) {
      setError('Las contraseñas no coinciden')
      return
    }
    if (form.password.length < 6) {
      setError('La contraseña debe tener al menos 6 caracteres')
      return
    }
    setLoading(true)
    try {
      await register({ username: form.username, email: form.email, name: form.name, password: form.password })
      const data = await login(form.username, form.password)
      saveToken(data.token)
      onSuccess(form.username)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <form className="modal-form" onSubmit={handleSubmit}>
      <div className="form-row">
        <div className="form-group">
          <label>Usuario <span className="required">*</span></label>
          <input
            type="text"
            placeholder="héroe_123"
            value={form.username}
            onChange={set('username')}
            maxLength={20}
            required
            autoFocus
          />
          <span className="form-hint">Máximo 20 caracteres</span>
        </div>
        <div className="form-group">
          <label>Nombre</label>
          <input
            type="text"
            placeholder="Tu nombre real"
            value={form.name}
            onChange={set('name')}
          />
        </div>
      </div>
      <div className="form-group">
        <label>Correo electrónico <span className="required">*</span></label>
        <input
          type="email"
          placeholder="heroe@fithero.com"
          value={form.email}
          onChange={set('email')}
          required
        />
      </div>
      <div className="form-row">
        <div className="form-group">
          <label>Contraseña <span className="required">*</span></label>
          <input
            type="password"
            placeholder="••••••••"
            value={form.password}
            onChange={set('password')}
            required
          />
        </div>
        <div className="form-group">
          <label>Confirmar contraseña <span className="required">*</span></label>
          <input
            type="password"
            placeholder="••••••••"
            value={form.confirm}
            onChange={set('confirm')}
            required
          />
        </div>
      </div>
      {error && <p className="form-error">{error}</p>}
      <button className="btn btn-primary btn-block" type="submit" disabled={loading}>
        {loading ? 'Creando cuenta...' : '⚔️ Unirme a FitHero'}
      </button>
      <p className="form-legal">
        Al registrarte aceptas el uso de tus datos para la app FitHero (TFG DAM 2026).
      </p>
    </form>
  )
}

export default function AuthModal({ onClose, onSuccess }) {
  const [tab, setTab] = useState('login')

  function switchTab(t) {
    setTab(t)
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>✕</button>

        <div className="modal-header">
          <span className="modal-logo">⚔️ FitHero</span>
          <p className="modal-subtitle">
            {tab === 'login' ? 'Bienvenido de vuelta, héroe' : 'Empieza tu aventura hoy'}
          </p>
        </div>

        <div className="modal-tabs">
          <button
            className={`modal-tab${tab === 'login' ? ' active' : ''}`}
            onClick={() => switchTab('login')}
          >
            Iniciar sesión
          </button>
          <button
            className={`modal-tab${tab === 'register' ? ' active' : ''}`}
            onClick={() => switchTab('register')}
          >
            Registrarse
          </button>
        </div>

        {tab === 'login'
          ? <LoginForm onSuccess={onSuccess} />
          : <RegisterForm onSuccess={onSuccess} />
        }
      </div>
    </div>
  )
}
