import { useCallback, useEffect, useState, type FormEvent } from "react";
import { criarCidadao, listarCidadaos } from "../api/cidadaos";
import { ApiError } from "../api/client";
import type { Cidadao } from "../types/cidadao";

const formularioVazio = () => ({
  nome: "",
  email: "",
  telefone: "",
});

export function CidadaosPage() {
  const [cidadaos, setCidadaos] = useState<Cidadao[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erroLista, setErroLista] = useState<string | null>(null);

  const [form, setForm] = useState(formularioVazio);
  const [erroForm, setErroForm] = useState<string | null>(null);
  const [salvando, setSalvando] = useState(false);

  const recarregar = useCallback(async () => {
    setCarregando(true);
    setErroLista(null);
    try {
      const lista = await listarCidadaos();
      setCidadaos(lista);
    } catch (e) {
      setErroLista(
        e instanceof ApiError ? e.message : "Não foi possível carregar a lista.",
      );
      setCidadaos([]);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    void recarregar();
  }, [recarregar]);

  async function enviar(e: FormEvent) {
    e.preventDefault();
    setErroForm(null);
    setSalvando(true);
    try {
      await criarCidadao({
        nome: form.nome.trim(),
        email: form.email.trim(),
        telefone: form.telefone.trim(),
      });
      setForm(formularioVazio());
      await recarregar();
    } catch (err) {
      setErroForm(
        err instanceof ApiError
          ? err.message
          : "Não foi possível cadastrar o cidadão.",
      );
    } finally {
      setSalvando(false);
    }
  }

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-2xl font-semibold text-emerald-900">Cidadãos</h1>
        <p className="mt-1 text-sm text-slate-600">
          Cadastre quem pode abrir solicitações urbanas.
        </p>
      </div>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Novo cidadão</h2>
        <form onSubmit={enviar} className="mt-4 grid gap-4 sm:grid-cols-2">
          <label className="block text-sm sm:col-span-2">
            <span className="text-slate-600">Nome *</span>
            <input
              required
              type="text"
              value={form.nome}
              onChange={(ev) => setForm({ ...form, nome: ev.target.value })}
              className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              placeholder="Maria Silva"
            />
          </label>
          <label className="block text-sm">
            <span className="text-slate-600">E-mail *</span>
            <input
              required
              type="email"
              value={form.email}
              onChange={(ev) => setForm({ ...form, email: ev.target.value })}
              className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              placeholder="maria@email.com"
            />
          </label>
          <label className="block text-sm">
            <span className="text-slate-600">Telefone</span>
            <input
              type="tel"
              value={form.telefone}
              onChange={(ev) => setForm({ ...form, telefone: ev.target.value })}
              className="mt-1 w-full rounded-lg border border-slate-300 px-3 py-2"
              placeholder="41999998888"
            />
          </label>
          {erroForm && (
            <p className="rounded-lg bg-amber-50 px-3 py-2 text-sm text-amber-900 sm:col-span-2">
              {erroForm}
            </p>
          )}
          <p className="sm:col-span-2">
            <button
              type="submit"
              disabled={salvando}
              className="rounded-lg bg-emerald-700 px-4 py-2 text-sm font-medium text-white hover:bg-emerald-800 disabled:opacity-50"
            >
              {salvando ? "Salvando…" : "Cadastrar"}
            </button>
          </p>
        </form>
      </section>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Cadastrados</h2>
        {erroLista && (
          <p className="mt-2 rounded-lg bg-red-50 px-3 py-2 text-sm text-red-800">
            {erroLista}
          </p>
        )}
        {carregando ? (
          <p className="mt-4 text-sm text-slate-500">Carregando…</p>
        ) : cidadaos.length === 0 ? (
          <p className="mt-4 text-sm text-slate-500">
            Nenhum cidadão ainda. Use o formulário acima.
          </p>
        ) : (
          <div className="mt-4 overflow-x-auto">
            <table className="w-full min-w-[32rem] text-left text-sm">
              <thead>
                <tr className="border-b border-slate-200 text-slate-500">
                  <th className="py-2 pr-4 font-medium">Nome</th>
                  <th className="py-2 pr-4 font-medium">E-mail</th>
                  <th className="py-2 pr-4 font-medium">Telefone</th>
                  <th className="py-2 font-medium">ID</th>
                </tr>
              </thead>
              <tbody>
                {cidadaos.map((c) => (
                  <tr key={c.id} className="border-b border-slate-100">
                    <td className="py-2 pr-4">{c.nome}</td>
                    <td className="py-2 pr-4">{c.email}</td>
                    <td className="py-2 pr-4">{c.telefone ?? "—"}</td>
                    <td className="py-2 font-mono text-xs text-slate-500">
                      {c.id}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </div>
  );
}
