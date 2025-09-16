import Head from 'next/head'
import { useEffect, useState } from 'react'
import { apiBase } from '../src/lib/config'

export default function Home() {
  const [apiStatus, setApiStatus] = useState<string>('loading...')

  useEffect(() => {
    const controller = new AbortController()
    ;(async () => {
      try {
        const res = await fetch(`${apiBase}/api/ping`, { signal: controller.signal })
        if (!res.ok) throw new Error(`HTTP ${res.status}`)
        const data = await res.json()
        setApiStatus(`API OK: ${data.message}`)
      } catch (err: any) {
        setApiStatus(`API 未连接：${err?.message || 'unknown error'}`)
      }
    })()
    return () => controller.abort()
  }, [])

  return (
    <>
      <Head>
        <title>TestSwap UI</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
      </Head>
      <main style={{ maxWidth: 720, margin: '40px auto', fontFamily: 'ui-sans-serif, system-ui' }}>
        <h1 style={{ fontSize: 28, fontWeight: 700 }}>TestSwap UI</h1>
        <p style={{ marginTop: 8, color: '#555' }}>最小可运行的前端骨架（Next.js）。</p>

        <section style={{ marginTop: 24 }}>
          <h2 style={{ fontSize: 18, fontWeight: 600 }}>后端连接状态</h2>
          <div style={{ padding: 12, background: '#f7f7f7', borderRadius: 8, marginTop: 8 }}>{apiStatus}</div>
          <p style={{ marginTop: 8, color: '#666' }}>
            默认后端监听 <code>{apiBase}</code>，当前页面尝试请求 <code>/api/ping</code>。
          </p>
        </section>
      </main>
    </>
  )
}
